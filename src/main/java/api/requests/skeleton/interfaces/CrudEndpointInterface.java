package api.requests.skeleton.interfaces;

import api.models.BaseModel;

public interface CrudEndpointInterface {
    Object post(BaseModel baseModel);

    Object post();

    Object get();

    Object put(BaseModel model);

    Object delete(BaseModel model);
}
