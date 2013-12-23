-- return tables' data
select c.relname as TABLE_NAME, obj_description(c.oid) as COMMENT from pg_catalog.pg_class c where c.relname like '%' and c.relname not like 'sql_%' and c.relname not like 'pg_%' and c.relkind = 'r' order by TABLE_NAME;

-- return fields data for a table
select a.attname as COLUMN_NAME,  
    pg_catalog.format_type(a.atttypid, a.atttypmod) as DATA_TYPE,   
    a.attnotnull as NOT_NULL,
    a.attndims as ARRAY_DIM_SIZE,
    a.atthasdef as HAS_DEFAULT_VALUE,
    col_description(c.oid, a.attnum) as COMMENTS
from pg_catalog.pg_class c, pg_catalog.pg_attribute a   
where c.relname = 'cal_email_queue_item'   
  and c.relkind = 'r'  
  and a.attrelid = c.oid  
  and a.attnum > 0   
  and a.attisdropped is false  
order by a.attnum;

-- return primary key for a table
SELECT               
  pg_attribute.attname, 
  format_type(pg_attribute.atttypid, pg_attribute.atttypmod) 
FROM pg_index, pg_class, pg_attribute 
WHERE 
  pg_class.oid = 'cal_email_queue_item'::regclass AND
  indrelid = pg_class.oid AND
  pg_attribute.attrelid = pg_class.oid AND 
  pg_attribute.attnum = any(pg_index.indkey)
  AND indisprimary;
