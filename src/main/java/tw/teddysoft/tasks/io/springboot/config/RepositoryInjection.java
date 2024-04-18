package tw.teddysoft.tasks.io.springboot.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import tw.teddysoft.tasks.adapter.out.repository.ToDoListCrudRepository;
import tw.teddysoft.tasks.usecase.port.out.ToDoListRepository;
import tw.teddysoft.tasks.adapter.out.repository.ToDoListCrudRepositoryPeer;

@PropertySource(value = "classpath:/application.properties")
@Configuration("ToDoListRepositoryInjection")
public class RepositoryInjection {

    private final ToDoListCrudRepositoryPeer toDoListCrudRepositoryPeer;

    @Autowired
    public RepositoryInjection(ToDoListCrudRepositoryPeer toDoListCrudRepositoryPeer) {
        this.toDoListCrudRepositoryPeer = toDoListCrudRepositoryPeer;
    }

    @Bean(name = "toDoListRepository")
    public ToDoListRepository toDoListRepository() {
        return new ToDoListCrudRepository(toDoListCrudRepositoryPeer);
    }

//    @Bean(name = "toDoListRepository")
//    public ToDoListRepository toDoListRepository() {
//        return new ToDoListInMemoryRepository(new ToDoListInMemoryRepositoryPeer());
//    }
}
