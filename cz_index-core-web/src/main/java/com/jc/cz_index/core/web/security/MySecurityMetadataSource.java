package com.jc.cz_index.core.web.security;

import org.springframework.security.access.SecurityMetadataSource;

public abstract interface MySecurityMetadataSource extends SecurityMetadataSource {
    public abstract void initAllConfigAttributes();
}
