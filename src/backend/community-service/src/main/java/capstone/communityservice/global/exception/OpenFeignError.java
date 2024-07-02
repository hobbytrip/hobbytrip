package capstone.communityservice.global.exception;

import feign.Response;
import feign.codec.ErrorDecoder;

public class OpenFeignError implements ErrorDecoder {
    @Override
    public Exception decode(String methodKey, Response response) {
        switch (response.status()){
            case 404:
                if(methodKey.contains("getUser")){
                    return new GlobalException("사용자가 존재하지 않습니다.");
                }
                else if(methodKey.contains("getServerUsersState")){

                }
                else if(methodKey.contains("getUsersConnectionState")){

                }
                else if(methodKey.contains("getUserLocation")){

                }
        }

        return null;
    }
}
