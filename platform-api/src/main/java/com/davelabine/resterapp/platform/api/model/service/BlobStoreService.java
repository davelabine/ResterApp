package com.davelabine.resterapp.platform.api.model.service;

import com.davelabine.resterapp.platform.api.model.model.BlobData;
import com.davelabine.resterapp.platform.api.model.model.BlobLocation;

/**
 * Created by davidl on 2/21/17.
 */
public interface BlobStoreService {
    /**
     * Put an object into the blobstore and retrieve the BlobLocation.
     * @param data a valid data object
     * @return
     */
    BlobLocation putObject(BlobData data);


    /**
     * get an object based on the BlobLocation provided.
     * @param key a populated blob location (bucket and object id)
     * @return the handle to the object data.
     */
    BlobData getObject(BlobLocation key);

    /**
     * get an object's URL based on the BlobLocation provided.
     * @param key a populated blob location (bucket and object id)
     * @return the handle to the object data.
     */
    String getObjectUrl(BlobLocation key);



    /**
     * perform a delete operation on the provided key.
     * @param key the location (including bucket and object ids).
     *
     */
    boolean deleteObject(BlobLocation key);
}