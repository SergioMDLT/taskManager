package com.example.taskManager.application.task.usecase;

/* import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort; */
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.taskManager.domain.task.Task;
import com.example.taskManager.domain.task.TaskRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class UpdateTaskPriorityUseCase {

    private final TaskRepository taskRepository;

    public UpdateTaskPriorityUseCase(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Transactional
    public void execute( Integer taskId, Integer newPriority ) {
        Task task = taskRepository.findById( taskId )
                .orElseThrow(() -> new EntityNotFoundException( "Task not found" ));

        Integer oldPriority = task.getPriority();
        if ( newPriority.equals( oldPriority )) {
            return;
        }

        String auth0Id = task.getUser().getAuth0Id();

        if ( oldPriority < newPriority ) {
            taskRepository.decrementPriorities( auth0Id, oldPriority + 1, newPriority );
        } else {
            taskRepository.incrementPriorities( auth0Id, newPriority, oldPriority - 1 );
        }

        task.setPriority( newPriority );
        taskRepository.save( task );

        //normalizePriorities(auth0Id);
    }

    /* private void normalizePriorities( String auth0Id ) {
        int page = 0;
        int size = 10; // Ajusta el tamaño según necesidad
        Pageable pageable = PageRequest.of( page, size, Sort.by( "priority" ));
        
        boolean needsUpdate = false;
        int expectedPriority = 1;
    
        while ( true ) {
            Page<Task> taskPage = taskRepository.findByUser_Auth0Id( auth0Id, pageable );
            if ( taskPage.isEmpty() ) break;
    
            List<Task> tasks = taskPage.getContent();
            for ( Task task : tasks ) {
                if ( !task.getPriority().equals( expectedPriority )) {
                    task.setPriority( expectedPriority );
                    needsUpdate = true;
                }
                expectedPriority++;
            }
    
            if ( needsUpdate ) {
                taskRepository.saveAll( tasks );
            }
    
            if ( !taskPage.hasNext() ) break;
            pageable = pageable.next();
        }
    } */
   
}
