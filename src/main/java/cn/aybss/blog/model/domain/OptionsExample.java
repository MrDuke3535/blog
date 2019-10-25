package cn.aybss.blog.model.domain;

import java.util.ArrayList;
import java.util.List;

public class OptionsExample {

    protected List<Criteria> oredCriteria;

    protected boolean distinct;

    protected String orderByClause;

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void setOredCriteria(List<Criteria> oredCriteria) {
        this.oredCriteria = oredCriteria;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public OptionsExample(){
        oredCriteria = new ArrayList<>();
    }

    public Criteria createCriteria(){
        Criteria criteria = createCriteriaInternal();
        if(oredCriteria.size()==0){
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal(){
        Criteria criteria = new Criteria();
        return criteria;
    }

    protected abstract static class GeneratedCriteria{
        protected List<Criterion> criteria;

        protected GeneratedCriteria(){
            super();
            criteria = new ArrayList<>();
        }

        public boolean isValid(){
            return criteria.size()>0;
        }

        public List<Criterion> getAllCriteria(){
            return criteria;
        }

        public List<Criterion> getCriteria(){
            return criteria;
        }

        protected void addCriterion(String condition){
            if(condition == null){
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition,Object value,String property){
            if(value == null){
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition,value));
        }

        protected void addCriterion(String condition,Object value1,Object value2,String property){
            if(value1 == null || value2 == null){
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition,value1,value2));
        }

        public Criteria andOptionNameEqualTo(String value){
            addCriterion("option_name=",value,"optionName");
            return (Criteria) this;
        }

    }

    public static class Criteria extends GeneratedCriteria{

        protected Criteria(){
            super();
        }

    }

    public static class Criterion{

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

        public void setCondition(String condition) {
            this.condition = condition;
        }

        public Object getValue() {
            return value;
        }

        public void setValue(Object value) {
            this.value = value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public void setSecondValue(Object secondValue) {
            this.secondValue = secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public void setNoValue(boolean noValue) {
            this.noValue = noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public void setSingleValue(boolean singleValue) {
            this.singleValue = singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public void setBetweenValue(boolean betweenValue) {
            this.betweenValue = betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public void setListValue(boolean listValue) {
            this.listValue = listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        public void setTypeHandler(String typeHandler) {
            this.typeHandler = typeHandler;
        }

        protected Criterion(String condition){
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition,Object value,String typeHandler){
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if(value instanceof List<?>){
                this.listValue = true;
            }else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition,Object value){
            this(condition,value,null);
        }

        protected Criterion(String condition,Object value,Object secondValue,String typeHandler){
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition,Object value,Object secondValue){
            this(condition,value,secondValue,null);
        }
    }
}
