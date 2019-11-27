package zhaoyy.poidemo.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * 此工具类通过反射获取当前对象为空的属性值，用来BeanUtils.copyProperties(object1,object2,ignore)种的ignore
 * @author zhaoyuyang
 * @createTime 2019/11/26 0026 17:03
 */
public class JudgeNullUtil {
    public static String[] getNullPropertiesArray(Object o1) throws IllegalAccessException {
        Field[] declaredFields = o1.getClass().getDeclaredFields();
        List<String> nullProperties = new ArrayList<>();
        for (Field declaredField : declaredFields) {
            //设置可以对private属性的对象进行访问
            declaredField.setAccessible(true);
            if(declaredField.get(o1)==null){
                // 将为空的属性添加到list中
                nullProperties.add(declaredField.getName());
            }
        }
        //nullProperties.stream().toArray(String[]::new);
        return nullProperties.toArray(new String[0]);
    }
}
