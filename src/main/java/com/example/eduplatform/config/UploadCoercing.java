package com.example.eduplatform.config;

import graphql.GraphQLContext;
import graphql.execution.CoercedVariables;
import graphql.language.Value;
import graphql.schema.Coercing;
import graphql.schema.CoercingParseLiteralException;
import graphql.schema.CoercingParseValueException;
import graphql.schema.CoercingSerializeException;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.springframework.web.multipart.MultipartFile;

import java.util.Locale;

public class UploadCoercing implements Coercing<MultipartFile, Void> {

    @Override
    public @Nullable Void serialize(@NonNull Object dataFetcherResult, @NonNull GraphQLContext graphQLContext, @NonNull Locale locale) throws CoercingSerializeException {
        throw new CoercingSerializeException("Upload scalar cannot be serialized");
    }

    @Override
    public @Nullable MultipartFile parseValue(@NonNull Object input, @NonNull GraphQLContext graphQLContext, @NonNull Locale locale) throws CoercingParseValueException {
        if (input instanceof MultipartFile multipartFile) {
            return multipartFile;
        }
        throw new CoercingParseValueException("Expected a MultipartFile object.");
    }

    @Override
    public @Nullable MultipartFile parseLiteral(@NonNull Value<?> input, @NonNull CoercedVariables variables, @NonNull GraphQLContext graphQLContext, @NonNull Locale locale) throws CoercingParseLiteralException {
        throw new CoercingParseLiteralException("Upload scalar does not support literal values");
    }
}
