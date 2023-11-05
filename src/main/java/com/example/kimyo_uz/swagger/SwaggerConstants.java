package com.example.kimyo_uz.swagger;

public class SwaggerConstants {

    public static final String EXAMPLE_RESPONSE_SUCCESS = """
            {
                            "success":"true",
                            "code":"0",
                            "message":"OK",
                            "data": {
                               "Value":"Value...",
                               "Value":"Value...",
                               "Value":"Value...",
                               "Value":"Value...",
                               "Value":"Value...",
                               ......:.........
                            }
                       }
               
                      """;

    public static final String EXAMPLE_RESPONSE_NOT_FOUND=
            """ 
                 {
                 "success":"false",
                 "code":"-1",
                 "message":"Product is not found!"
            }
                    

                    """;
}
