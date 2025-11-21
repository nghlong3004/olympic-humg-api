package vn.edu.humg.log;

public interface LogField<T> {
    String getKey();

    String getValue(T source);
}
