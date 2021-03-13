package UIL.model;

import BLL.Registry;
import BLL.Ticket;
import javafx.beans.property.SimpleStringProperty;

public class QueueModel {
    private final SimpleStringProperty ticketId;
    private final SimpleStringProperty name;
    private final SimpleStringProperty priority;

    public QueueModel(Ticket ticket) {
        this.ticketId = new SimpleStringProperty(ticket.getId());
        this.name = new SimpleStringProperty(ticket.getName());
        switch (ticket.getPriority()) {
            case 1: {
                this.priority = new SimpleStringProperty("复诊");
                break;
            }
            case 2: {
                this.priority = new SimpleStringProperty("初诊");
                break;
            }
            default: {
                this.priority = new SimpleStringProperty("加急");
            }
        }
    }

    public String getTicketId() {
        return ticketId.get();
    }

    public void setTicketId(String ticketId) {
        this.ticketId.set(ticketId);
    }

    public Ticket getTicket() {
        Registry registry = Registry.getInstance();
        return registry.getById(getTicketId());
    }

    public SimpleStringProperty ticketIdProperty() {
        return ticketId;
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public String getPriority() {
        return priority.get();
    }

    public void setPriority(String priority) {
        this.priority.set(priority);
    }

    public SimpleStringProperty priorityProperty() {
        return priority;
    }
}
