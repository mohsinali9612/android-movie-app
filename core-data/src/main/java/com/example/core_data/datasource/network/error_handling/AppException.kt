package com.example.core_data.datasource.network.error_handling

import com.example.utils_extension.utils.ExtConstants.NetworkConstants.ERROR_400
import com.example.utils_extension.utils.ExtConstants.NetworkConstants.ERROR_OCCURRED
import com.example.utils_extension.utils.ExtConstants.NetworkConstants.SESSION_EXPIRED_CODE


class AppException : Exception {

    private var errorMsg: String
    private var errCode: Int = 0
    private var errorLog: String?=""
    var isSessionExpired:Boolean?=false

    constructor(errCode: Int?=null, error: String?, errorLog: String? = "") : super(error) {
        this.errorMsg = error ?: ERROR_OCCURRED
        this.errCode = errCode ?: ERROR_400
        this.errorLog = errorLog?:this.errorMsg
        this.isSessionExpired = errCode == SESSION_EXPIRED_CODE
    }

    constructor(errCode: Int?=null, error: String?) : super(error) {
        this.errorMsg = error ?: ERROR_OCCURRED
        this.errCode = errCode ?: ERROR_400
        this.isSessionExpired = errCode == SESSION_EXPIRED_CODE
    }

    constructor(error: Errors, e: Throwable?) {
        errCode = error.getKey()
        errorMsg = error.getValue()
        errorLog = e?.message
        isSessionExpired = error.getKey()== SESSION_EXPIRED_CODE
    }
}