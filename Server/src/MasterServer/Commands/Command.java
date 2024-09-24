package MasterServer.Commands;

import Common.DTO.BaseResponse;

/**
 * Абстрактный класс от которого наследуются все существующие команды
 * Служит как метка для удобства хранения в словаре
 * Содержит поля и методы, которые обязаны реализовывать все команды наследники
 */
public abstract class Command {
    /**
     * Информация о том, что делает команда
     */
    private String description;
    /**
     * Информация о том, принимает ли команда аргументы
     */
    private boolean iHaveArgument;

    private boolean onlyServer;

    private boolean isGeneral;

    /**
     * Конструктор принимает в качестве аргументов описание и информацию о наличии аргумента
     * @param description
     * @param iHaveArgument
     */
    public Command(String description, boolean iHaveArgument)
    {
        this.description = description;
        this.iHaveArgument = iHaveArgument;
    }

    /**
     * Метод в котором у наследников прописана логика выполнения команды
     * @param arguments
     * @return информацию о результате выполнения
     */
    public abstract BaseResponse execute(String arguments);

    /**
     * Возвращает описание команды
     * @return
     */
    public String getDescription() {
        return description;
    }

    /**
     * Возвращает информацию о наличии аргумента у команды
     * @return
     */
    public boolean getHasArgument(){return iHaveArgument;}

    public void setOnlyServer(boolean value) {
        onlyServer = value;
    }

    public boolean getOnlyServer(){
        return onlyServer;
    }

    public void setGeneral(boolean value) {
        isGeneral = value;
    }

    public boolean getGeneral() {
        return isGeneral;
    }
}
