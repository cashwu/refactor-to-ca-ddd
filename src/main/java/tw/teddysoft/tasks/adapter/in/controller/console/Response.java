package tw.teddysoft.tasks.adapter.in.controller.console;

public record Response<T> (T result) {
    public static <T> Response<T> of(T result) {
        return new Response<>(result);
    }
}
