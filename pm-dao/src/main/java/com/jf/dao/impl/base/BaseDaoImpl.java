package com.jf.dao.impl.base;

import com.jf.dao.base.BaseDao;
import com.jf.entity.base.SimpleEntity;
import com.jf.search.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import java.math.BigDecimal;
import java.util.*;

/**
 * *************************************************************************************************
 * <br>
 * 实现功能：
 * <br>
 * ------------------------------------------------------------------------------------------------
 * <br>
 * 版本          变更时间             变更人                     变更原因
 * <br>
 * ------------------------------------------------------------------------------------------------
 * <br>
 * 1.0.00      2017/5/8 15:33       fly                        新建
 * <br>
 * *************************************************************************************************
 */
@SuppressWarnings("ALL")
@Transactional(readOnly = true)
public class BaseDaoImpl<T extends SimpleEntity> extends SimpleJpaRepository<T, String> implements BaseDao<T> {
    private static final Logger logger = LoggerFactory.getLogger(BaseDaoImpl.class);

    protected final Class<T> domainClass;
    protected final EntityManager entityManager;

    public BaseDaoImpl(Class<T> domainClass, EntityManager entityManager) {
        super(domainClass, entityManager);
        this.domainClass = domainClass;
        this.entityManager = entityManager;
    }

    /**
     * 获取业务实体类型
     *
     * @return 业务实体类型
     */
    @Override
    public Class<T> getEntityClass() {
        return domainClass;
    }


    /**
     * 根据泛型对象属性和值查询唯一对象
     *
     * @param property 属性名，即对象中数量变量名称
     * @param value    参数值
     * @return 未查询到返回null，如果查询到多条数据则返回第一条
     */
    @Transactional(readOnly = true)
    public T findFirstByProperty(final String property, final Object value) {
        Pageable pageable = new PageRequest(0, 1);
        Specification<T> spec = (root, query, builder) -> {
            Expression expression = buildExpression(root, builder, property, null);
            return builder.equal(expression, value);
        };
        Page<T> page = findAll(spec, pageable);
        T result = null;
        if (Objects.nonNull(page)) {
            List<T> list = page.getContent();
            if (CollectionUtils.isNotEmpty(list)) {
                result = list.get(0);
            }
        }
        return result;
    }

    /**
     * 根据业务对象属性的值判断业务实体是否存在
     *
     * @param property 属性名，即对象中数量变量名称
     * @param value    参数值
     * @return 未查询到返回false，如果查询到一条或多条数据则返回true
     */
    @Override
    public boolean isExists(String property, Object value) {
        return Objects.nonNull(findFirstByProperty(property, value));
    }

    /**
     * 单一条件对象查询数据集合
     */
    @Transactional(readOnly = true)
    public List<T> findByFilter(SearchFilter searchFilter) {
        List<SearchFilter> searchFilters = new ArrayList<SearchFilter>();
        searchFilters.add(searchFilter);
        Search searchConfig = new Search(searchFilters);
        Specification<T> spec = buildSpecification(searchConfig);
        return findAll(spec);
    }

    /**
     * 单一条件对象查询唯一数据
     */
    @Transactional(readOnly = true)
    public T findUniqueByFilter(SearchFilter searchFilter) {
        List<SearchFilter> searchFilters = new ArrayList<SearchFilter>();
        searchFilters.add(searchFilter);
        Search searchConfig = new Search(searchFilters);
        Specification<T> spec = buildSpecification(searchConfig);
        return findOne(spec);
    }

    /**
     * 基于查询条件count记录数据
     */
    @Transactional(readOnly = true)
    public long count(Search searchConfig) {
        Specification<T> spec = buildSpecification(searchConfig);
        return count(spec);
    }

    /**
     * 基于动态组合条件对象和排序定义查询数据集合
     */
    @Transactional(readOnly = true)
    public List<T> findByFilters(Search searchConfig) {
        Sort sort = buildSort(searchConfig);
        Specification<T> spec = buildSpecification(searchConfig);
        return findAll(spec, sort);
    }

    /**
     * 基于动态组合条件对象和分页(含排序)对象查询数据集合
     */
    @Transactional(readOnly = true)
    public PageResult findPage(Search searchConfig) {
        PageInfo pageInfo = searchConfig.getPageInfo();
        Assert.isTrue(pageInfo != null, "无分页参数。");

        Sort sort = buildSort(searchConfig);
        Pageable pageable = new PageRequest(pageInfo.getPage() - 1, pageInfo.getRows(), sort);

        Specification<T> specifications = buildSpecification(searchConfig);

        Page<T> page = findAll(specifications, pageable);
        PageResult<T> pageResult = new PageResult<T>();
        pageResult.setPage(page.getNumber() + 1);
        pageResult.setRecords(page.getTotalElements());
        pageResult.setTotal(page.getTotalPages());
        pageResult.setRows(page.getContent());
        return pageResult;
    }

    private Sort buildSort(Search searchConfig) {
        Sort sort = null;
        if (searchConfig != null) {
            List<SearchOrder> sortOrders = searchConfig.getSortOrders();
            if (sortOrders != null) {
                List<Sort.Order> orders = new LinkedList<Sort.Order>();
                for (SearchOrder sortOrder : sortOrders) {
                    if (SearchOrder.Direction.DESC.equals(sortOrder.getDirection())) {
                        orders.add(new Sort.Order(Sort.Direction.DESC, sortOrder.getProperty()));
                    } else {
                        orders.add(new Sort.Order(Sort.Direction.ASC, sortOrder.getProperty()));
                    }
                }
                sort = new Sort(orders);
            }
        }
        return sort;
    }

    private <X> Predicate buildPredicate(String propertyName, SearchFilter filter, Root<X> root, CriteriaQuery<?> query, CriteriaBuilder builder,
                                         Boolean having) {
        Object matchValue = filter.getValue();

        if (matchValue == null) {
            return null;
        }
        if (having && !propertyName.contains("(")) {
            return null;
        }
        if (!having && propertyName.contains("(")) {
            return null;
        }
        if (matchValue instanceof String) {
            if (StringUtils.isBlank(String.valueOf(matchValue))) {
                return null;
            }
        } else if (matchValue.getClass().isArray()) {
            if (((Object[]) matchValue).length == 0) {
                return null;
            }
        } else if (matchValue instanceof Collection) {
            if (((Collection) matchValue).size() == 0) {
                return null;
            }
        }

        Predicate predicate = null;

        Expression expression = buildExpression(root, builder, propertyName, null);

        if (SearchFilter.NULL_VALUE.equalsIgnoreCase(String.valueOf(matchValue))) {
            return expression.isNull();
        } else if (SearchFilter.EMPTY_VALUE.equalsIgnoreCase(String.valueOf(matchValue))) {
            return builder.or(builder.isNull(expression), builder.equal(expression, ""));
        } else if (SearchFilter.NO_NULL_VALUE.equalsIgnoreCase(String.valueOf(matchValue))) {
            return expression.isNotNull();
        } else if (SearchFilter.NO_EMPTY_VALUE.equalsIgnoreCase(String.valueOf(matchValue))) {
            return builder.and(builder.isNotNull(expression), builder.notEqual(expression, ""));
        }

        // logic operator
        switch (filter.getOperator()) {
            case EQ:
                // 对日期特殊处理：一般用于区间日期的结束时间查询,如查询2012-01-01之前,一般需要显示2010-01-01当天及以前的数据,
                // 而数据库一般存有时分秒,因此需要特殊处理把当前日期+1天,转换为<2012-01-02进行查询
                if (matchValue instanceof Date) {
                    DateTime dateTime = new DateTime(((Date) matchValue).getTime());
                    if (dateTime.getHourOfDay() == 0 && dateTime.getMinuteOfHour() == 0 && dateTime.getSecondOfMinute() == 0) {
                        return builder.and(builder.greaterThanOrEqualTo(expression, dateTime.toDate()),
                                builder.lessThan(expression, dateTime.plusDays(1).toDate()));
                    }
                }
                predicate = builder.equal(expression, matchValue);
                break;
            case NE:
                // 对日期特殊处理：一般用于区间日期的结束时间查询,如查询2012-01-01之前,一般需要显示2010-01-01当天及以前的数据,
                // 而数据库一般存有时分秒,因此需要特殊处理把当前日期+1天,转换为<2012-01-02进行查询
                if (matchValue instanceof Date) {
                    DateTime dateTime = new DateTime(((Date) matchValue).getTime());
                    if (dateTime.getHourOfDay() == 0 && dateTime.getMinuteOfHour() == 0 && dateTime.getSecondOfMinute() == 0) {
                        return builder.or(builder.lessThan(expression, dateTime.toDate()),
                                builder.greaterThanOrEqualTo(expression, dateTime.plusDays(1).toDate()));
                    }
                }
                predicate = builder.notEqual(expression, matchValue);
                break;
            case BK://IS NULL OR ==''
                predicate = builder.or(builder.isNull(expression), builder.equal(expression, ""));
                break;
            case NB://IS NOT NULL AND !=''
                predicate = builder.and(builder.isNotNull(expression), builder.notEqual(expression, ""));
                break;
            case NU://IS NULL
                if (matchValue instanceof Boolean && !((Boolean) matchValue)) {
                    predicate = builder.isNotNull(expression);
                } else {
                    predicate = builder.isNull(expression);
                }
                break;
            case NN://IS NOT NULL
                if (matchValue instanceof Boolean && !((Boolean) matchValue)) {
                    predicate = builder.isNull(expression);
                } else {
                    predicate = builder.isNotNull(expression);
                }
                break;
            case LK://LIKE %abc%
                predicate = builder.like(expression, "%" + matchValue + "%");
                break;
            case NC://NOT LIKE %abc%
                predicate = builder.notLike(expression, "%" + matchValue + "%");
                break;
            case LLK://LIKE abc%
                predicate = builder.like(expression, matchValue + "%");
                break;
            case BN://NOT LIKE abc%
                predicate = builder.notLike(expression, matchValue + "%");
                break;
            case RLK://LIKE %abc
                predicate = builder.like(expression, "%" + matchValue);
                break;
            case EN://NOT LIKE %abc
                predicate = builder.notLike(expression, "%" + matchValue);
                break;
            case BT://BETWEEN 1 AND 2
                Assert.isTrue(matchValue.getClass().isArray(), "Match value must be array");
                Object[] matchValues = (Object[]) matchValue;
                Assert.isTrue(matchValues.length == 2, "Match value must have two value");
                // 对日期特殊处理：一般用于区间日期的结束时间查询,如查询2012-01-01之前,一般需要显示2010-01-01当天及以前的数据,
                // 而数据库一般存有时分秒,因此需要特殊处理把当前日期+1天,转换为<2012-01-02进行查询
                if (matchValues[0] instanceof Date) {
                    DateTime dateFrom = new DateTime(((Date) matchValues[0]).getTime());
                    DateTime dateTo = new DateTime(((Date) matchValues[1]).getTime());
                    if (dateFrom.getHourOfDay() == 0 && dateFrom.getMinuteOfHour() == 0 && dateFrom.getSecondOfMinute() == 0) {
                        return builder.and(builder.greaterThanOrEqualTo(expression, dateFrom.toDate()),
                                builder.lessThan(expression, dateTo.plusDays(1).toDate()));
                    }
                } else {
                    return builder.between(expression, (Comparable) matchValues[0], (Comparable) matchValues[1]);
                }
                predicate = builder.equal(expression, matchValue);
                break;
            case GT://>
                predicate = builder.greaterThan(expression, (Comparable) matchValue);
                break;
            case GE://>=
                predicate = builder.greaterThanOrEqualTo(expression, (Comparable) matchValue);
                break;
            case LT://<
                // 对日期特殊处理：一般用于区间日期的结束时间查询,如查询2012-01-01之前,一般需要显示2010-01-01当天及以前的数据,
                // 而数据库一般存有时分秒,因此需要特殊处理把当前日期+1天,转换为<2012-01-02进行查询
                if (matchValue instanceof Date) {
                    DateTime dateTime = new DateTime(((Date) matchValue).getTime());
                    if (dateTime.getHourOfDay() == 0 && dateTime.getMinuteOfHour() == 0 && dateTime.getSecondOfMinute() == 0) {
                        return builder.lessThan(expression, dateTime.plusDays(1).toDate());
                    }
                }
                predicate = builder.lessThan(expression, (Comparable) matchValue);
                break;
            case LE://<=
                // 对日期特殊处理：一般用于区间日期的结束时间查询,如查询2012-01-01之前,一般需要显示2010-01-01当天及以前的数据,
                // 而数据库一般存有时分秒,因此需要特殊处理把当前日期+1天,转换为<2012-01-02进行查询
                if (matchValue instanceof Date) {
                    DateTime dateTime = new DateTime(((Date) matchValue).getTime());
                    if (dateTime.getHourOfDay() == 0 && dateTime.getMinuteOfHour() == 0 && dateTime.getSecondOfMinute() == 0) {
                        return builder.lessThan(expression, dateTime.plusDays(1).toDate());
                    }
                }
                predicate = builder.lessThanOrEqualTo(expression, (Comparable) matchValue);
                break;
            case IN:
                if (matchValue.getClass().isArray()) {
                    predicate = expression.in((Object[]) matchValue);
                } else if (matchValue instanceof Collection) {
                    predicate = expression.in((Collection) matchValue);
                } else {
                    predicate = builder.equal(expression, matchValue);
                }
                break;
            case PLT://Property Less Equal: <
                Expression expressionPLT = buildExpression(root, builder, (String) matchValue, null);
                predicate = builder.lessThan(expression, expressionPLT);
                break;
            case PLE://Property Less Than: <=
                Expression expressionPLE = buildExpression(root, builder, (String) matchValue, null);
                predicate = builder.lessThanOrEqualTo(expression, expressionPLE);
                break;
            default:
                break;
        }

        Assert.notNull(predicate, "Undefined match type: " + filter.getOperator());
        return predicate;
    }

    /**
     * 根据条件集合对象组装JPA规范条件查询集合对象，基类默认实现进行条件封装组合
     * 子类可以调用此方法在返回的List<Predicate>额外追加其他PropertyFilter不易表单的条件如exist条件处理等
     */
    private <X> List<Predicate> buildPredicatesFromFilters(final Collection<SearchFilter> filters, Root<X> root, CriteriaQuery<?> query,
                                                           CriteriaBuilder builder, Boolean having) {
        List<Predicate> predicates = new ArrayList<Predicate>();
        if (CollectionUtils.isNotEmpty(filters)) {
            for (SearchFilter filter : filters) {
                Predicate predicate = buildPredicate(filter.getFieldName(), filter, root, query, builder, having);
                if (predicate != null) {
                    predicates.add(predicate);
                }
            }
        }
        return predicates;
    }

    private <X extends SimpleEntity> Specification<X> buildSpecification(final Search searchConfig) {
        return (root, query, builder) -> {
            if (searchConfig != null) {
                return buildPredicatesFromFilters(searchConfig, root, query, builder, false);
            } else {
                return null;
            }
        };
    }

    private Predicate buildPredicatesFromFilters(Search searchConfig, Root root, CriteriaQuery<?> query, CriteriaBuilder builder, Boolean having) {
        if (searchConfig == null) {
            return null;
        }
        Predicate predicate = null;
        List<Predicate> predicates = new ArrayList<Predicate>();
        //快速搜索
        Collection<String> quickSearchProperties = searchConfig.getQuickSearchProperties();
        String quickSearchValue = searchConfig.getQuickSearchValue();
        if (CollectionUtils.isNotEmpty(quickSearchProperties) && StringUtils.isNotBlank(quickSearchValue)) {
            List<SearchFilter> searchFilters = new ArrayList<SearchFilter>();
            for (String property : quickSearchProperties) {
                searchFilters.add(new SearchFilter(property, quickSearchValue, SearchFilter.Operator.LK));
            }
            List<Predicate> quickSearchPredicates = buildPredicatesFromFilters(searchFilters, root, query, builder, having);

            if (CollectionUtils.isNotEmpty(quickSearchPredicates)) {
                //若有多个，存在or分组
                if (quickSearchPredicates.size() == 1) {
                    predicate = builder.and(quickSearchPredicates.get(0));
                } else {
                    //分组
                    predicate = builder.or(quickSearchPredicates.toArray(new Predicate[quickSearchPredicates.size()]));
                }
                if (predicate != null) {
                    predicates.add(predicate);
                }
            }
        }
        //追加普通查询条件（and）
        List<Predicate> andPredicates = buildPredicatesFromFilters(searchConfig.getFilters(), root, query, builder, having);
        if (CollectionUtils.isNotEmpty(andPredicates)) {
            predicates.addAll(andPredicates);
        }
        if (predicates.size() > 0) {
            predicate = builder.and(predicates.toArray(new Predicate[predicates.size()]));
        }
        return predicate;
    }

    private Expression parseExpr(Root<?> root, CriteriaBuilder criteriaBuilder, String expr, Map<String, Expression<?>> parsedExprMap) {
        if (parsedExprMap == null) {
            parsedExprMap = new HashMap<String, Expression<?>>();
        }
        Expression<?> expression = null;
        if (expr.contains("(")) {
            int left = 0;
            char[] chars = expr.toCharArray();
            for (int i = 0; i < chars.length; i++) {
                if (chars[i] == '(') {
                    left = i;
                }
            }
            String leftStr = expr.substring(0, left);
            String op = null;
            char[] leftStrs = leftStr.toCharArray();
            for (int i = leftStrs.length - 1; i > 0; i--) {
                if (leftStrs[i] == '(' || leftStrs[i] == ')' || leftStrs[i] == ',') {
                    op = leftStr.substring(i + 1);
                    break;
                }
            }
            if (op == null) {
                op = leftStr;
            }
            String rightStr = expr.substring(left + 1);
            String arg = StringUtils.substringBefore(rightStr, ")");
            String[] args = arg.split(",");
            //logger.debug("op={},arg={}", op, arg);
            if (op.equalsIgnoreCase("case")) {
                CriteriaBuilder.Case selectCase = criteriaBuilder.selectCase();

                Expression caseWhen = parsedExprMap.get(args[0]);

                String whenResultExpr = args[1];
                Object whenResult = parsedExprMap.get(whenResultExpr);
                if (whenResult == null) {
                    CriteriaBuilder.Case<Long> whenCase = selectCase.when(caseWhen, new BigDecimal(whenResultExpr));
                    selectCase = whenCase;
                } else {
                    CriteriaBuilder.Case<Expression<?>> whenCase = selectCase.when(caseWhen, whenResult);
                    selectCase = whenCase;
                }
                String otherwiseResultExpr = args[2];
                Object otherwiseResult = parsedExprMap.get(otherwiseResultExpr);
                if (otherwiseResult == null) {
                    expression = selectCase.otherwise(new BigDecimal(otherwiseResultExpr));
                } else {
                    expression = selectCase.otherwise((Expression<?>) otherwiseResult);
                }
            } else {
                Object[] subExpressions = new Object[args.length];
                for (int i = 0; i < args.length; i++) {
                    subExpressions[i] = parsedExprMap.get(args[i]);
                    if (subExpressions[i] == null) {
                        String name = args[i];
                        try {
                            Path<?> item;
                            if (name.contains(".")) {
                                String[] props = StringUtils.split(name, ".");
                                item = root.get(props[0]);
                                for (int j = 1; j < props.length; j++) {
                                    item = item.get(props[j]);
                                }
                            } else {
                                item = root.get(name);
                            }
                            subExpressions[i] = item;
                        } catch (Exception e) {
                            subExpressions[i] = new BigDecimal(name);
                        }
                    }
                }
                try {
                    //criteriaBuilder.quot();
                    expression = (Expression) MethodUtils.invokeMethod(criteriaBuilder, op, subExpressions);
                } catch (Exception e) {
                    logger.error("Error for aggregate  setting ", e);
                }
            }

            String exprPart = op + "(" + arg + ")";
            String exprPartConvert = exprPart.replace(op + "(", op + "_").replace(arg + ")", arg + "_").replace(",", "_");
            expr = expr.replace(exprPart, exprPartConvert);
            parsedExprMap.put(exprPartConvert, expression);

            if (expr.contains("(")) {
                expression = parseExpr(root, criteriaBuilder, expr, parsedExprMap);
            }
        } else {
            Path<?> item;
            if (expr.contains(".")) {
                String[] props = StringUtils.split(expr, ".");
                item = root.get(props[0]);
                for (int j = 1; j < props.length; j++) {
                    item = item.get(props[j]);
                }
            } else {
                item = root.get(expr);
            }
            expression = item;
        }
        return expression;
    }

    private Expression<?> buildExpression(Root<?> root, CriteriaBuilder criteriaBuilder, String name, String alias) {
        Expression<?> expr = parseExpr(root, criteriaBuilder, name, null);
        if (alias != null) {
            expr.alias(alias);
        }
        return expr;
    }

}
