package com.example.jobis.dto.jobis;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class SzsScrapResult {

    @SerializedName("status")
    private String status;
    @SerializedName("data")
    private Data data;

    @Getter @Setter
    public class Data {
        @SerializedName("jsonList")
        private JsonList jsonList;

        @Getter @Setter
        public class JsonList {
            @SerializedName("급여")
            private List<Salary> salarys;
            @SerializedName("산출세액")
            private String calculatedTax;
            @SerializedName("소득공제")
            private List<IncomeDeduction> incomeDeductions;

            @Getter @Setter
            public class Salary {
                @SerializedName("소득구분")
                private String incomeClassification;
                @SerializedName("총지급액")
                private String totalInCome;
                @SerializedName("기업명")
                private String company;
                @SerializedName("이름")
                private String name;
            }

            @Getter @Setter
            public class IncomeDeduction {
                @SerializedName("소득구분")
                private String incomeClassification;
                @SerializedName(value = "amount", alternate = {"총납임금액","금액"})
                private String amount;
            }

        }

    }



}
