package Common.DTO;

import java.io.Serial;
import java.io.Serializable;

/**
 * Класс хранит информацию о результате выполнения команды
 */
public class BaseResponse implements Serializable {
    @Serial
    private static final long serialVersionUID = 1863735568972913284L;

    /**
     * Информация о том, выполнилась команда или нет
     */
    private final boolean execution;
    /**
     * Описание результата выполнения/не выполнения команды
     */
    private final String description;

    private final boolean visibleLog;

    private boolean shutDown = false;

    /**
     * В конструктор передается информация о выполнении и описании
     * @param execution Информация о том, выполнилась команда или нет
     * @param description Описание результата выполнения/не выполнения команды
     */
    public BaseResponse(boolean execution, String description){
        this.execution = execution;
        this.description = description;
        this.visibleLog = true;
    }

    public BaseResponse(boolean execution, String description, boolean visibleLog){
        this.execution = execution;
        this.description = description;
        this.visibleLog = visibleLog;
    }

    /**
     * В конструктор передается информация только о выполнении
     * @param execution Информация о том, выполнилась команда или нет
     */
    public BaseResponse(boolean execution){
        this.execution = execution;
        this.description = null;
        this.visibleLog = false;
    }

    /**
     * Возвращает описание результата выполнения/не выполнения команды
     * @return description
     */
    public String getDescription(){
        return this.description;
    }

    /**
     * Возвращает описание результата выполнения/не выполнения команды
     * @return execution
     */
    public boolean getExecution(){
        return this.execution;
    }

    public void setShutDown(){
        this.shutDown = true;
    }

    public boolean getShutDown(){
        return this.shutDown;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Response -> Execution: '").append(execution).append("'; ");
        if(visibleLog){
            sb.append("Description: '").append(description).append("'");
        }
        return sb.toString();
    }
}
