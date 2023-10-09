package com.example.opt_1.model;

/**
 * CRUD Callbacks ensures that Firebase async methods work as expected
 */
public interface CRUDCallbacks {
    void onSucceed();
    void onFailure();
}
