package com.example.utils_extension.enums

enum class CoroutineDispatcherModuleEnums(val dispatcherName:String){
    DEFAULT("defaultDispatcher"),
    IO("ioDispatcher"),
    MAIN("mainDispatcher"),
    MAIN_IMMEDIATE("mainImmediateDispatcher"),
}