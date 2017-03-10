package com.davelabine.resterapp.platform.api.model;

import lombok.Builder;
import lombok.Getter;



/**
 * Created by davidl on 2/21/17.
 */
@Getter
@Builder
public class BlobLocation {
    private String bucketName;
    private String key;
}
