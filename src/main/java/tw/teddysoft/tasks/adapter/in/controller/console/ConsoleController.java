package tw.teddysoft.tasks.adapter.in.controller.console;

public interface ConsoleController<T> {
    Response<T> execute(Request request);
}
