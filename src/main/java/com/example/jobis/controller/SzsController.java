package com.example.jobis.controller;

import com.example.jobis.dto.request.szs.Login;
import com.example.jobis.dto.request.szs.Scrap;
import com.example.jobis.dto.request.szs.SignUp;
import com.example.jobis.dto.response.*;
import com.example.jobis.service.SzsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;

@Tag(name="삼쩜삼", description = "유저의 환급액 계산 기능")
@RestController
@RequestMapping(value = "/szs", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
//@RequestMapping(value = "/szs")
public class SzsController {

    @Autowired
    SzsService service;

    @Operation(summary = "회원가입 요청")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", content = @Content(schema = @Schema(implementation = GeneralResponse.class))),
            @ApiResponse(responseCode = "403", description = "Fail", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @RequestMapping(value = "/signup", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public GeneralResponse SignUp(@Parameter @RequestBody @Validated SignUp request) throws Exception {

        return service.SignUp(request);
    }

    @Operation(summary = "회원 Token 발급요청")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", content = @Content(schema = @Schema(implementation = LoginResponse.class))),
            @ApiResponse(responseCode = "403", description = "Fail", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public LoginResponse Login(@Parameter @RequestBody @Validated Login request) throws Exception {

        return service.Login(request);
    }

    @Operation(summary = "가입한 회원정보 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", content = @Content(schema = @Schema(implementation = MeResponse.class))),
            @ApiResponse(responseCode = "403", description = "Fail", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @RequestMapping(value = "/me", method = RequestMethod.GET, consumes = "*/*")
    public MeResponse Me(@Parameter(name = "token", required = true, example = "eyJhbGciOiJub25lIn0.eyJ1c2VySWQiOiJob25nMTIiLCJwYXNzd29yZCI6IjEyMzQ1NiIsIm5hbWUiOiLtmY3quLjrj5kiLCJyZWdObyI6Ijg2MDgyNC0xNjU1MDY4In0.")
                             @RequestHeader(name = "token") @NotEmpty String token) throws Exception {

        return service.Me(token);
    }

    @Operation(summary = "가입한 회원 세부정보 스크랩")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", content = @Content(schema = @Schema(implementation = GeneralResponse.class))),
            @ApiResponse(responseCode = "403", description = "Fail", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @RequestMapping(value = "/scrap", method = RequestMethod.POST)
    public GeneralResponse Scrap(@Parameter @RequestBody @Validated Scrap request) throws Exception {

        return service.Scrap(request);
    }

    @Operation(summary = "회원 결정세액, 퇴근연금세액공제금액 계산 요청")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", content = @Content(schema = @Schema(implementation = RefundResponse.class))),
            @ApiResponse(responseCode = "403", description = "Fail", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @RequestMapping(value = "/refund", method = RequestMethod.GET, consumes = "*/*")
    public RefundResponse Refund(@Parameter(name = "token", required = true, example = "eyJhbGciOiJub25lIn0.eyJ1c2VySWQiOiJob25nMTIiLCJwYXNzd29yZCI6IjEyMzQ1NiIsIm5hbWUiOiLtmY3quLjrj5kiLCJyZWdObyI6Ijg2MDgyNC0xNjU1MDY4In0.")
                                     @RequestHeader(name = "token") @NotEmpty String token) throws Exception {

        return service.Refund(token);
    }





}
