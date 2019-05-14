package com.link.feeling.framework.utils.data;

import java.util.Collection;

/**
 * Created on 2019/1/3  19:08
 * chenpan pan.chen@linkfeeling.cn
 *
 * 集合
 */
@SuppressWarnings("unused")
public final class CollectionsUtil {

    // 集合为空
    public static boolean isEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    // 集合size
    public static int size(Collection<?> collection) {
        return collection != null ? collection.size() : 0;
    }

    // 集合不为空
    public static boolean isNotEmpty(Collection<?> collection) {
        return !(collection == null || collection.isEmpty());
    }

    // 清空
    public static void clear(Collection<?> collection) {
        if (!isEmpty(collection)) {
            collection.clear();
        }
    }
}
