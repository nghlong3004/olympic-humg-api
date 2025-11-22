package vn.edu.humg.olympic.api.log;

public interface LogField<T> {
    String getKey();

    String getValue(T source);
}
