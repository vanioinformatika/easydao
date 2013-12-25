-- return tables' data
select ut.table_name as TABLE_NAME, tc.comments as COMMENTS
    from USER_TABLES ut, USER_TAB_COMMENTS tc
    where tc.table_name = ut.table_name and ut.table_name not like '%$%';

-- return fields data for a table
select utc.column_name,
    decode(utc.char_used, 'C', utc.char_length, utc.data_length) as DATA_LENGTH,
    case when utc.data_type = 'NUMBER' then lower(concat(concat(concat(concat(concat(utc.data_type, '('), utc.data_precision), ','), utc.data_scale), ')'))
        when utc.data_type = 'VARCHAR2' then lower(concat(concat(concat(utc.data_type, '('), DATA_LENGTH), ')'))
        else lower(utc.data_type)
    end as DATA_TYPE_PRE,
    lower(DATA_TYPE_PRE) as DATA_TYPE,
    case when utc.nullable = 'Y' then 1
        else 0
    end as NOT_NULL,
    0 as ARRAY_DIM_SIZE,
    null as HAS_DEFAULT_VALUE,
    ucc.comments as COMMENTS
from user_tab_cols utc, user_col_comments ucc
where utc.table_name = ? and ucc.table_name = utc.table_name and ucc.column_name = utc.column_name;

-- return primary key for a table
select column_name
    from user_cons_columns c, user_constraints t
    where c.table_name = upper('ir_ugyek') and
    t.constraint_type = 'P' and
    t.constraint_name = c.constraint_name
    order by c.position