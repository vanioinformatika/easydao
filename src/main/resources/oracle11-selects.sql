-- return tables' data
select ut.table_name as TABLE_NAME, tc.comments as COMMENTS
    from USER_TABLES ut, USER_TAB_COMMENTS tc
    where tc.table_name = ut.table_name;

-- return fields data for a table
select utc.column_name,
    decode(utc.char_used, 'C', utc.char_length, utc.data_length) as data_length,
    case when utc.data_type = 'NUMBER' then lower(concat(concat(concat(concat(concat(utc.data_type, '('), utc.data_precision), ','), utc.data_scale), ')'))
        when utc.data_type = 'VARCHAR2' then lower(concat(concat(concat(utc.data_type, '('), DATA_LENGTH), ')'))
        else lower(utc.data_type)
    end as data_type_pre,
    lower(data_type_pre) as data_type,
    case when utc.nullable = 'Y' then 1
        else 0
    end as NOT_NULL,
    0 as array_dim_size,
    null as has_default_value,
    ucc.comments as comments
from user_tab_cols utc, user_col_comments ucc
where utc.table_name = ? and ucc.table_name = utc.table_name and ucc.column_name = utc.column_name;

-- return primary key for a table
select COLUMN_NAME
    from user_cons_columns c, user_constraints t
    where c.TABLE_NAME=upper('ir_ugyek') and
    t.CONSTRAINT_TYPE='P' and
    t.CONSTRAINT_NAME=c.CONSTRAINT_NAME
    order by c.position