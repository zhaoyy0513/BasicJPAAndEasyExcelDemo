package zhaoyy.poidemo.util;

import com.alibaba.fastjson.JSONObject;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

/**
 * @author zhaoyuyang
 * @createTime 2019/11/27 0027 10:24
 */
public class PageEntity<E> {
    private Integer page;
    private Integer limit;
    private long total;
    private List<E> data;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<E> getData() {
        return data;
    }

    public void setData(List<E> data) {
        this.data = data;
    }

    public static PageEntity getInstance(Page page) {
        PageEntity pageEntity = new PageEntity();
        //数据总数
        pageEntity.setTotal(page.getTotalElements());
        // 每页显示的数据大小limit
        pageEntity.setLimit(page.getSize());
        // 根据数据总数和limit获取的总页数，因为pageable默认是从0开始的，因此这里为了前台显示方便，所以+1
        pageEntity.setPage(page.getNumber()+1);
        //按照当前页数、分页大小，查出的分页结果集
        pageEntity.setData(page.getContent());
        return pageEntity;
    }
}
