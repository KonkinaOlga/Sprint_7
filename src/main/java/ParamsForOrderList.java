public class ParamsForOrderList {

    private Number limit;
    private Number page;

    public ParamsForOrderList(Number limit, Number page) {
        this.limit = limit;
        this.page = page;
    }

    public Number getLimit() {
        return limit;
    }

    public void setLimit(Number limit) {
        this.limit = limit;
    }

    public Number getPage() {
        return page;
    }

    public void setPage(Number page) {
        this.page = page;
    }

}
