package com.example.eduplatform.exception;

public class DuplicateModuleIndexException extends RuntimeException {

    public DuplicateModuleIndexException(Integer moduleIndex) {
        super("Module index " + moduleIndex + " already exists for this course");
    }

}
