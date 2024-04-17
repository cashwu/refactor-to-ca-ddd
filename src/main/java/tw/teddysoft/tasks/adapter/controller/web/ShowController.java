package tw.teddysoft.tasks.adapter.controller.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import tw.teddysoft.ezddd.core.usecase.ExitCode;
import tw.teddysoft.ezddd.cqrs.usecase.CqrsOutput;
import tw.teddysoft.tasks.io.springboot.config.UseCaseInjection;
import tw.teddysoft.tasks.io.standard.ToDoListApp;
import tw.teddysoft.tasks.usecase.port.in.todolist.show.ShowInput;
import tw.teddysoft.tasks.usecase.port.in.todolist.show.ShowUseCase;

@RestController
@AutoConfigureAfter({UseCaseInjection.class})
public class ShowController {

    private final ShowUseCase showUseCase;

    @Autowired
    public ShowController(ShowUseCase showUseCase) {
        this.showUseCase = showUseCase;
    }

    @GetMapping(path = "/show")
    public ResponseEntity<CqrsOutput> help() {
        try {
            ShowInput input = new ShowInput();
            input.toDoListId = ToDoListApp.DEFAULT_TO_DO_LIST_ID;
            var output = showUseCase.execute(input);
            if (output.getExitCode() == ExitCode.SUCCESS)
                return new ResponseEntity<>(output, HttpStatus.OK);
            return new ResponseEntity<>(output, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            var output = CqrsOutput.create()
                    .setExitCode(ExitCode.FAILURE)
                    .setMessage("Cannot display help" + e.getMessage());
            return new ResponseEntity<>(output, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
