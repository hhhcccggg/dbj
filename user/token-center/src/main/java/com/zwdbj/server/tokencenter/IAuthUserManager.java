package com.zwdbj.server.tokencenter;

import com.zwdbj.server.tokencenter.model.AuthUser;

public interface IAuthUserManager {
    AuthUser get(String id);
}
