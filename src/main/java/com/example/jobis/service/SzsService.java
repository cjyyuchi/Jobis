package com.example.jobis.service;

import com.example.jobis.dto.Member;
import com.example.jobis.dto.SzsScrap;
import com.example.jobis.dto.jobis.SzsScrapResult;
import com.example.jobis.dto.request.szs.Scrap;
import com.example.jobis.dto.response.MeResponse;
import com.example.jobis.dto.response.RefundResponse;
import com.example.jobis.entity.MemberEntity;
import com.example.jobis.dto.request.szs.Login;
import com.example.jobis.dto.request.szs.SignUp;
import com.example.jobis.dto.response.GeneralResponse;
import com.example.jobis.dto.response.LoginResponse;
import com.example.jobis.entity.SzsScrapEntity;
import com.example.jobis.exception.BusinessException;
import com.example.jobis.repository.MemberRepository;
import com.example.jobis.repository.MemberWhiteListRepository;
import com.example.jobis.repository.SzsScrapRepository;
import com.example.jobis.util.BigDecimalUtil;
import com.example.jobis.util.JerseyClientUtil;
import com.example.jobis.util.JsonUtil;
import com.example.jobis.util.JwtUtil;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
@Slf4j
public class SzsService {

    @Autowired
    MemberRepository memberRepo;

    @Autowired
    MemberWhiteListRepository memberWhiteListRepo;

    @Autowired
    SzsScrapRepository szsScrapRepo;

    @Autowired
    ModelMapper modelMapper;

    Gson gson = new Gson();

    public GeneralResponse SignUp(SignUp request) throws Exception {

        // 특정 유저 회원가입 가능여부 검증
        boolean availableSignUp = memberWhiteListRepo.existsByNameAndRegNo(request.getName(), request.getRegNo());

        if ( !availableSignUp ){
            throw new BusinessException(1001, "회원 가입이 가능한 유저가 아닙니다.");
        }

        // 기 가입여부 검증
        boolean isSignUp = memberRepo.existsByUserId(request.getUserId());

        if ( isSignUp ){
            throw new BusinessException(1002, "이미 가입된 회원정보 입니다.");
        }

        // 회원정보 등록
        MemberEntity member = new MemberEntity(request.getUserId(), request.getPassword(), request.getName(), request.getRegNo());
        memberRepo.save(member);

        return new GeneralResponse();
    }

    public LoginResponse Login(Login request) throws Exception {

        // 회원여부 검증
        MemberEntity member = memberRepo.findFirstByUserIdAndPassword(request.getUserId(), request.getPassword());

        if (member == null){
            throw new BusinessException(1002, "요청하신 회원정보가 존재하지 않습니다.");
        }

        // JWT 토큰 생성
        String token = JwtUtil.makeMemberJwtToken(JsonUtil.toJson(member));

        
        LoginResponse response = new LoginResponse(token);

        return response;
    }

    public MeResponse Me(String token) throws Exception {

        // JWT Token 복호화
        String body = JwtUtil.readJwtTokenBody(token);

        // Member 매핑
        Member jwtMember = JsonUtil.toMember(body);

        // DB 재조회하여 데이터 불일치 검증 (선택)
        MemberEntity entityMember = memberRepo.findFirstByUserIdAndPassword(jwtMember.getUserId(), jwtMember.getPassword());
        Member member = JsonUtil.toMember(JsonUtil.toJson(entityMember));

        if (member == null){
            throw new BusinessException(1002, "요청하신 회원정보가 존재하지 않습니다.");
        }

        return new MeResponse(member);
    }

    @Transactional
    public GeneralResponse Scrap(Scrap request) throws Exception {

        // 회원여부 검증
        MemberEntity entityMember = memberRepo.findFirstByNameAndRegNo(request.getName(), request.getRegNo());

        if (entityMember == null){
            throw new BusinessException(1002, "요청하신 회원정보가 존재하지 않습니다.");
        }

        Member member = modelMapper.map(entityMember, Member.class);


        // 데이터 스크랩 API 호출
        HashMap<String, Object> body = new HashMap<>();
        body.put("name", request.getName());
        body.put("regNo", request.getRegNo());

        String response = JerseyClientUtil.get("https://codetest.3o3.co.kr/v2/scrap", body);
        SzsScrapResult szsScrapResult = JsonUtil.toSzsScrap(response);

        
        // 기존 스크랩 정보 DB 초기화
        szsScrapRepo.deleteByUserId(member.getUserId());


        // 신규 스크랩 정보 DB 저장
        List<SzsScrapEntity> szsScrapEntities = filterSzsScraps(member.getUserId(), szsScrapResult);

        szsScrapRepo.saveAll(szsScrapEntities);


        return new GeneralResponse();
    }


    public RefundResponse Refund(String token) throws Exception {

        // JWT Token을 통한 회원정보 조회
        String body = JwtUtil.readJwtTokenBody(token);
        Member jwtMember = JsonUtil.toMember(body);

        // 회원여부 검증
        MemberEntity entityMember = memberRepo.findFirstByUserId(jwtMember.getUserId());

        if (entityMember == null){
            throw new BusinessException(1002, "요청하신 회원정보가 존재하지 않습니다.");
        }

        Member member = modelMapper.map(entityMember, Member.class);

        // 결정세액 산출정보 조회
        List<SzsScrapEntity> szsScrapEntities = szsScrapRepo.findByUserId(member.getUserId());

        if (szsScrapEntities.size() == 0){
            throw new BusinessException(1101, "사용자의 스크랩 정보가 존재하지 않습니다. \\n스크랩 이후 다시 요청 부탁드립니다.");
        }


        // 결정세액 산출정보 계산
        List<SzsScrap> szsScraps = new ArrayList<>();

        for (SzsScrapEntity szsScrapEntity : szsScrapEntities){
            szsScraps.add(modelMapper.map(szsScrapEntity, SzsScrap.class));
        }

        String[] calcResult = CalcDeterminedTaxAmount(szsScraps);


        return new RefundResponse(member.getName(), calcResult[0], calcResult[1]);
    }


    // 결정세액, 퇴직연금세액공제금액 계산
    private String[] CalcDeterminedTaxAmount(List<SzsScrap> szsScraps){

        BigDecimal eterminedTaxAmount = new BigDecimal(0); // 결정세액

        BigDecimal calculatedTaxAmount = new BigDecimal(0); // 산출세액
        BigDecimal totalSalaryAmount = new BigDecimal(0); // 총급여

        BigDecimal workAmount = new BigDecimal(0); // 근로소득세액공제,  산출세액 * 0.55
        BigDecimal retirementAmount = new BigDecimal(0); // 퇴직연금세액공제금액, 퇴직연금납입금액 * 0.15

        BigDecimal commonAmount = new BigDecimal(0); // 표준세액공제금액 합
        BigDecimal totalSpecialAmount = new BigDecimal(0); // 특별세액공제금액 합
        BigDecimal insuranceAmount = new BigDecimal(0); // 특별세액공제금액 > 보험료, 보험료납입금액 * 0.12
        BigDecimal medicalAmount = new BigDecimal(0); // 특별세액공제금액 > 의료비, (의료납입금액 - 총금여 * 0.03) * 0.15, 마이너스인 경우 0원
        BigDecimal educationAmount = new BigDecimal(0); // 특별세액공제금액 > 교육비, 교육비납입금액 * 0.15
        BigDecimal donationAmount = new BigDecimal(0); // 특별세액공제금액 > 기부금, 기부금납입금액 * 0.15

        for (SzsScrap szsScrap : szsScraps){
            switch (szsScrap.getIncomeClassification()){
                case "총급여":
                    totalSalaryAmount = szsScrap.getAmount();
                    break;
                case "산출세액":
                    calculatedTaxAmount = szsScrap.getAmount();
                    workAmount = szsScrap.getAmount().multiply(new BigDecimal(0.55));
                    break;
                case "퇴직연금":
                    retirementAmount = szsScrap.getAmount().multiply(new BigDecimal(0.15));
                    break;
                case "보험료":
                    insuranceAmount = szsScrap.getAmount().multiply(new BigDecimal(0.12));
                    break;
                case "의료비":
                    medicalAmount = szsScrap.getAmount(); // 총급여 관련되므로 차후 계산
                    break;
                case "교육비":
                    educationAmount = szsScrap.getAmount().multiply(new BigDecimal(0.15));
                    break;
                case "기부금":
                    donationAmount = szsScrap.getAmount().multiply(new BigDecimal(0.15));
                    break;
                default:
                    log.warn("결정세액 계산 잔여정보 :: {}, {}", szsScrap.getIncomeClassification(), szsScrap.getAmount());
            }
        }

        // 의료비공제금액 추가 계산 (특별세액공제금액 > 의료비, (의료납입금액 - 총금여 * 0.03) * 0.15, 마이너스인 경우 0원)
        medicalAmount = medicalAmount.subtract( totalSalaryAmount.multiply(new BigDecimal(0.03)) ).multiply(new BigDecimal(0.15));
        if ( medicalAmount.compareTo(new BigDecimal(0)) < 0 )
            medicalAmount = new BigDecimal(0);

        // 표준세액공제금액 추가 계산 (특별세액공제금액의 합 < 130,000 = 130,000, >= 130,000 = 0), 단, 표준세액공제금액 = 130,000원이면 특별세액공제금액 = 0 처리
        totalSpecialAmount = insuranceAmount.add(medicalAmount).add(educationAmount).add(donationAmount);
        if ( totalSpecialAmount.compareTo(new BigDecimal(130000)) < 0 ) {
            commonAmount = new BigDecimal(130000);
            totalSpecialAmount = new BigDecimal(0);
        }
        else {
            commonAmount = new BigDecimal(0);
        }

        // 결정세액 = 산출세액 - 근로소득세액공제금액 - 특별세액공제금액 - 표준세액공제금액 - 퇴직연금세액공제금액
        eterminedTaxAmount = calculatedTaxAmount.subtract(workAmount).subtract(totalSpecialAmount).subtract(commonAmount).subtract(retirementAmount);
        if ( eterminedTaxAmount.compareTo(new BigDecimal(0)) < 0 )
            eterminedTaxAmount = new BigDecimal(0);

        String[] result = {BigDecimalUtil.toCommaStr(eterminedTaxAmount), BigDecimalUtil.toCommaStr(retirementAmount)};

        return result;
    }


    private List<SzsScrapEntity> filterSzsScraps(String userId, SzsScrapResult szsScrapResult) throws ParseException {

        List<SzsScrapEntity> szsScrapEntities = new ArrayList<>();
        DecimalFormat dFmt = new DecimalFormat("###,###,###");
        dFmt.setParseBigDecimal(true);

        // 총 급여
        List<SzsScrapResult.Data.JsonList.Salary> salarys = szsScrapResult.getData().getJsonList().getSalarys();
        BigDecimal totSalary = new BigDecimal(0);
        for (SzsScrapResult.Data.JsonList.Salary salary : salarys){
            totSalary = totSalary.add( BigDecimalUtil.fromCommaStr(salary.getTotalInCome()) );
        }
        szsScrapEntities.add(new SzsScrapEntity(userId, "총급여", totSalary));

        // 산출세액
        BigDecimal calculatedTax = BigDecimalUtil.fromCommaStr(szsScrapResult.getData().getJsonList().getCalculatedTax());
        szsScrapEntities.add(new SzsScrapEntity(userId, "산출세액", calculatedTax));

        // 특별세액
        List<SzsScrapResult.Data.JsonList.IncomeDeduction> incomeDeductions = szsScrapResult.getData().getJsonList().getIncomeDeductions();
        for(SzsScrapResult.Data.JsonList.IncomeDeduction deduction : incomeDeductions){
            szsScrapEntities.add(new SzsScrapEntity(userId, deduction.getIncomeClassification(), BigDecimalUtil.fromCommaStr(deduction.getAmount())));
        }

        return szsScrapEntities;
    }



}
