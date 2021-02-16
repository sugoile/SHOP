package com.xsg.shop.mbg.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PmsProductPromotionPriceExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public PmsProductPromotionPriceExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Long value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Long> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andProductIdIsNull() {
            addCriterion("product_id is null");
            return (Criteria) this;
        }

        public Criteria andProductIdIsNotNull() {
            addCriterion("product_id is not null");
            return (Criteria) this;
        }

        public Criteria andProductIdEqualTo(Long value) {
            addCriterion("product_id =", value, "productId");
            return (Criteria) this;
        }

        public Criteria andProductIdNotEqualTo(Long value) {
            addCriterion("product_id <>", value, "productId");
            return (Criteria) this;
        }

        public Criteria andProductIdGreaterThan(Long value) {
            addCriterion("product_id >", value, "productId");
            return (Criteria) this;
        }

        public Criteria andProductIdGreaterThanOrEqualTo(Long value) {
            addCriterion("product_id >=", value, "productId");
            return (Criteria) this;
        }

        public Criteria andProductIdLessThan(Long value) {
            addCriterion("product_id <", value, "productId");
            return (Criteria) this;
        }

        public Criteria andProductIdLessThanOrEqualTo(Long value) {
            addCriterion("product_id <=", value, "productId");
            return (Criteria) this;
        }

        public Criteria andProductIdIn(List<Long> values) {
            addCriterion("product_id in", values, "productId");
            return (Criteria) this;
        }

        public Criteria andProductIdNotIn(List<Long> values) {
            addCriterion("product_id not in", values, "productId");
            return (Criteria) this;
        }

        public Criteria andProductIdBetween(Long value1, Long value2) {
            addCriterion("product_id between", value1, value2, "productId");
            return (Criteria) this;
        }

        public Criteria andProductIdNotBetween(Long value1, Long value2) {
            addCriterion("product_id not between", value1, value2, "productId");
            return (Criteria) this;
        }

        public Criteria andPromotionPriceIsNull() {
            addCriterion("promotion_price is null");
            return (Criteria) this;
        }

        public Criteria andPromotionPriceIsNotNull() {
            addCriterion("promotion_price is not null");
            return (Criteria) this;
        }

        public Criteria andPromotionPriceEqualTo(BigDecimal value) {
            addCriterion("promotion_price =", value, "promotionPrice");
            return (Criteria) this;
        }

        public Criteria andPromotionPriceNotEqualTo(BigDecimal value) {
            addCriterion("promotion_price <>", value, "promotionPrice");
            return (Criteria) this;
        }

        public Criteria andPromotionPriceGreaterThan(BigDecimal value) {
            addCriterion("promotion_price >", value, "promotionPrice");
            return (Criteria) this;
        }

        public Criteria andPromotionPriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("promotion_price >=", value, "promotionPrice");
            return (Criteria) this;
        }

        public Criteria andPromotionPriceLessThan(BigDecimal value) {
            addCriterion("promotion_price <", value, "promotionPrice");
            return (Criteria) this;
        }

        public Criteria andPromotionPriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("promotion_price <=", value, "promotionPrice");
            return (Criteria) this;
        }

        public Criteria andPromotionPriceIn(List<BigDecimal> values) {
            addCriterion("promotion_price in", values, "promotionPrice");
            return (Criteria) this;
        }

        public Criteria andPromotionPriceNotIn(List<BigDecimal> values) {
            addCriterion("promotion_price not in", values, "promotionPrice");
            return (Criteria) this;
        }

        public Criteria andPromotionPriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("promotion_price between", value1, value2, "promotionPrice");
            return (Criteria) this;
        }

        public Criteria andPromotionPriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("promotion_price not between", value1, value2, "promotionPrice");
            return (Criteria) this;
        }

        public Criteria andPromotionStartIsNull() {
            addCriterion("promotion_start is null");
            return (Criteria) this;
        }

        public Criteria andPromotionStartIsNotNull() {
            addCriterion("promotion_start is not null");
            return (Criteria) this;
        }

        public Criteria andPromotionStartEqualTo(Date value) {
            addCriterion("promotion_start =", value, "promotionStart");
            return (Criteria) this;
        }

        public Criteria andPromotionStartNotEqualTo(Date value) {
            addCriterion("promotion_start <>", value, "promotionStart");
            return (Criteria) this;
        }

        public Criteria andPromotionStartGreaterThan(Date value) {
            addCriterion("promotion_start >", value, "promotionStart");
            return (Criteria) this;
        }

        public Criteria andPromotionStartGreaterThanOrEqualTo(Date value) {
            addCriterion("promotion_start >=", value, "promotionStart");
            return (Criteria) this;
        }

        public Criteria andPromotionStartLessThan(Date value) {
            addCriterion("promotion_start <", value, "promotionStart");
            return (Criteria) this;
        }

        public Criteria andPromotionStartLessThanOrEqualTo(Date value) {
            addCriterion("promotion_start <=", value, "promotionStart");
            return (Criteria) this;
        }

        public Criteria andPromotionStartIn(List<Date> values) {
            addCriterion("promotion_start in", values, "promotionStart");
            return (Criteria) this;
        }

        public Criteria andPromotionStartNotIn(List<Date> values) {
            addCriterion("promotion_start not in", values, "promotionStart");
            return (Criteria) this;
        }

        public Criteria andPromotionStartBetween(Date value1, Date value2) {
            addCriterion("promotion_start between", value1, value2, "promotionStart");
            return (Criteria) this;
        }

        public Criteria andPromotionStartNotBetween(Date value1, Date value2) {
            addCriterion("promotion_start not between", value1, value2, "promotionStart");
            return (Criteria) this;
        }

        public Criteria andPromotionEndIsNull() {
            addCriterion("promotion_end is null");
            return (Criteria) this;
        }

        public Criteria andPromotionEndIsNotNull() {
            addCriterion("promotion_end is not null");
            return (Criteria) this;
        }

        public Criteria andPromotionEndEqualTo(Date value) {
            addCriterion("promotion_end =", value, "promotionEnd");
            return (Criteria) this;
        }

        public Criteria andPromotionEndNotEqualTo(Date value) {
            addCriterion("promotion_end <>", value, "promotionEnd");
            return (Criteria) this;
        }

        public Criteria andPromotionEndGreaterThan(Date value) {
            addCriterion("promotion_end >", value, "promotionEnd");
            return (Criteria) this;
        }

        public Criteria andPromotionEndGreaterThanOrEqualTo(Date value) {
            addCriterion("promotion_end >=", value, "promotionEnd");
            return (Criteria) this;
        }

        public Criteria andPromotionEndLessThan(Date value) {
            addCriterion("promotion_end <", value, "promotionEnd");
            return (Criteria) this;
        }

        public Criteria andPromotionEndLessThanOrEqualTo(Date value) {
            addCriterion("promotion_end <=", value, "promotionEnd");
            return (Criteria) this;
        }

        public Criteria andPromotionEndIn(List<Date> values) {
            addCriterion("promotion_end in", values, "promotionEnd");
            return (Criteria) this;
        }

        public Criteria andPromotionEndNotIn(List<Date> values) {
            addCriterion("promotion_end not in", values, "promotionEnd");
            return (Criteria) this;
        }

        public Criteria andPromotionEndBetween(Date value1, Date value2) {
            addCriterion("promotion_end between", value1, value2, "promotionEnd");
            return (Criteria) this;
        }

        public Criteria andPromotionEndNotBetween(Date value1, Date value2) {
            addCriterion("promotion_end not between", value1, value2, "promotionEnd");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}