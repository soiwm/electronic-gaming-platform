-- 同步序列值脚本
-- 用于修复切换数据库后序列值与表中最大ID不匹配的问题
-- 执行前请确保已连接到正确的数据库（pdbadmin）

-- 方法：使用 ALTER SEQUENCE RESTART（Oracle 12c+）
-- 如果您的 Oracle 版本不支持 RESTART，请参考方法2

-- 同步游戏序列
DECLARE
    v_max_id NUMBER;
    v_new_start NUMBER;
BEGIN
    SELECT NVL(MAX(ID), 0) INTO v_max_id FROM GAMES;
    v_new_start := v_max_id + 1;
    
    -- 重置序列，从最大ID+1开始
    EXECUTE IMMEDIATE 'ALTER SEQUENCE GAMES_SEQ RESTART START WITH ' || v_new_start;
    DBMS_OUTPUT.PUT_LINE('GAMES_SEQ 已重置为: ' || v_new_start);
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('GAMES_SEQ 重置失败: ' || SQLERRM);
END;
/

-- 同步客户序列
DECLARE
    v_max_id NUMBER;
    v_new_start NUMBER;
BEGIN
    SELECT NVL(MAX(ID), 0) INTO v_max_id FROM CUSTOMERS;
    v_new_start := v_max_id + 1;
    
    EXECUTE IMMEDIATE 'ALTER SEQUENCE CUSTOMERS_SEQ RESTART START WITH ' || v_new_start;
    DBMS_OUTPUT.PUT_LINE('CUSTOMERS_SEQ 已重置为: ' || v_new_start);
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('CUSTOMERS_SEQ 重置失败: ' || SQLERRM);
END;
/

-- 同步订单序列
DECLARE
    v_max_id NUMBER;
    v_new_start NUMBER;
BEGIN
    SELECT NVL(MAX(ID), 0) INTO v_max_id FROM ORDERS;
    v_new_start := v_max_id + 1;
    
    EXECUTE IMMEDIATE 'ALTER SEQUENCE ORDERS_SEQ RESTART START WITH ' || v_new_start;
    DBMS_OUTPUT.PUT_LINE('ORDERS_SEQ 已重置为: ' || v_new_start);
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('ORDERS_SEQ 重置失败: ' || SQLERRM);
END;
/

-- 同步用户游戏库序列
DECLARE
    v_max_id NUMBER;
    v_new_start NUMBER;
BEGIN
    SELECT NVL(MAX(ID), 0) INTO v_max_id FROM USER_GAME_LIBRARY;
    v_new_start := v_max_id + 1;
    
    EXECUTE IMMEDIATE 'ALTER SEQUENCE USER_GAME_LIBRARY_SEQ RESTART START WITH ' || v_new_start;
    DBMS_OUTPUT.PUT_LINE('USER_GAME_LIBRARY_SEQ 已重置为: ' || v_new_start);
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('USER_GAME_LIBRARY_SEQ 重置失败: ' || SQLERRM);
END;
/

-- 如果上面的 ALTER SEQUENCE RESTART 不支持（Oracle 11g及以下），请使用下面的方法：
/*
-- 方法2：DROP 和 CREATE（适用于所有 Oracle 版本）

-- 同步游戏序列
DECLARE
    v_max_id NUMBER;
    v_increment NUMBER;
    v_min_value NUMBER;
    v_max_value NUMBER;
    v_cache_size NUMBER;
    v_cycle VARCHAR2(10);
    v_sql VARCHAR2(4000);
BEGIN
    SELECT NVL(MAX(ID), 0) + 1 INTO v_max_id FROM GAMES;
    
    -- 获取序列属性
    SELECT INCREMENT_BY, MIN_VALUE, 
           CASE WHEN MAX_VALUE IS NULL THEN NULL ELSE MAX_VALUE END,
           CASE WHEN CACHE_SIZE = 0 THEN 0 ELSE CACHE_SIZE END,
           CASE WHEN CYCLE_FLAG = 'Y' THEN 'CYCLE' ELSE 'NOCYCLE' END
    INTO v_increment, v_min_value, v_max_value, v_cache_size, v_cycle
    FROM USER_SEQUENCES
    WHERE SEQUENCE_NAME = 'GAMES_SEQ';
    
    -- 删除并重建序列
    EXECUTE IMMEDIATE 'DROP SEQUENCE GAMES_SEQ';
    
    v_sql := 'CREATE SEQUENCE GAMES_SEQ 
        INCREMENT BY ' || v_increment || '
        START WITH ' || v_max_id || '
        MINVALUE ' || v_min_value;
    
    IF v_max_value IS NULL THEN
        v_sql := v_sql || ' NOMAXVALUE';
    ELSE
        v_sql := v_sql || ' MAXVALUE ' || v_max_value;
    END IF;
    
    IF v_cache_size = 0 THEN
        v_sql := v_sql || ' NOCACHE';
    ELSE
        v_sql := v_sql || ' CACHE ' || v_cache_size;
    END IF;
    
    v_sql := v_sql || ' ' || v_cycle;
    
    EXECUTE IMMEDIATE v_sql;
    DBMS_OUTPUT.PUT_LINE('GAMES_SEQ 已重建，起始值: ' || v_max_id);
END;
/

-- 类似地处理其他序列（CUSTOMERS_SEQ, ORDERS_SEQ, USER_GAME_LIBRARY_SEQ）...
*/

-- 验证序列值（查看序列的LAST_NUMBER，这是下一个将被使用的值）
SELECT SEQUENCE_NAME, LAST_NUMBER AS NEXT_VALUE,
       CASE SEQUENCE_NAME
           WHEN 'GAMES_SEQ' THEN (SELECT MAX(ID) FROM GAMES)
           WHEN 'CUSTOMERS_SEQ' THEN (SELECT MAX(ID) FROM CUSTOMERS)
           WHEN 'ORDERS_SEQ' THEN (SELECT MAX(ID) FROM ORDERS)
           WHEN 'USER_GAME_LIBRARY_SEQ' THEN (SELECT MAX(ID) FROM USER_GAME_LIBRARY)
       END AS MAX_TABLE_ID,
       CASE 
           WHEN LAST_NUMBER > CASE SEQUENCE_NAME
               WHEN 'GAMES_SEQ' THEN (SELECT MAX(ID) FROM GAMES)
               WHEN 'CUSTOMERS_SEQ' THEN (SELECT MAX(ID) FROM CUSTOMERS)
               WHEN 'ORDERS_SEQ' THEN (SELECT MAX(ID) FROM ORDERS)
               WHEN 'USER_GAME_LIBRARY_SEQ' THEN (SELECT MAX(ID) FROM USER_GAME_LIBRARY)
           END THEN '✓ 正常'
           ELSE '✗ 需要同步'
       END AS STATUS
FROM USER_SEQUENCES
WHERE SEQUENCE_NAME IN ('GAMES_SEQ', 'CUSTOMERS_SEQ', 'ORDERS_SEQ', 'USER_GAME_LIBRARY_SEQ')
ORDER BY SEQUENCE_NAME;

COMMIT;
