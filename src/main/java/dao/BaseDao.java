package dao;

import model.ResultMessage;

public interface BaseDao {
     ResultMessage save(Object bean) ;

     Object load(Class c,int id);

     ResultMessage update(Object bean) ;

     ResultMessage delete(Object bean) ;

}
