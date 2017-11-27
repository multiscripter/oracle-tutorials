package oracle.gettingstarted;

/**
 * Класс GettingStarted реализует проверку работоспособности репоза.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-11-27
 */
class GettingStarted {
    /**
     * Сообщение.
     */
    private final String message;
    /**
     * Конструктор без параметров.
     */
    GettingStarted() {
        this.message = "Сообщение по умолчанию.";
    }
    /**
     * Конструктор.
     * @param message сообщение.
     */
    GettingStarted(String message) {
        this.message = message.equals("") ? "Сообщение по умолчанию." : message;
    }
    /**
     * Получает сообщение.
     * @return сообщение.
     */
    public String getMessage() {
        return this.message;
    }
}