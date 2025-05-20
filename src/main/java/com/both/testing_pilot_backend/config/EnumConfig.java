package com.both.testing_pilot_backend.config;

import com.both.testing_pilot_backend.utils.OtpPurpose;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

@MappedTypes(OtpPurpose.class)
public class EnumConfig  extends BaseTypeHandler<OtpPurpose> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, OtpPurpose parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, parameter.name());
    }

    @Override
    public OtpPurpose getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String value = rs.getString(columnName);
        return value == null ? null : OtpPurpose.valueOf(value);
    }

    @Override
    public OtpPurpose getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String value = rs.getString(columnIndex);
        return value == null ? null : OtpPurpose.valueOf(value);
    }

    @Override
    public OtpPurpose getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String value = cs.getString(columnIndex);
        return value == null ? null : OtpPurpose.valueOf(value);
    }
}