package resources;

import services.MessageService;

public class Stockpile {
    private int[] allResources;
    private final MessageService messageService;
    public Stockpile(MessageService messageService, int[] defaultValues) {
        this.allResources = defaultValues;
        this.messageService = messageService;
    }

    public int[] getAllResources() {
        return allResources;
    }

    public void setAllResources(int[] allResources) {
        this.allResources = allResources;
    }

    public int getResource(int index) {
        return allResources[index];
    }

    public void setResource(int index, int resource) {
        this.allResources[index] = resource;
    }

    public void useResource(int resourceIndex, int amount) {
        if (isAvailable(getResource(resourceIndex), amount)) {
            this.setResource(resourceIndex, getResource(resourceIndex) - amount);
            messageService.stockpileInfo(getAllResources());
        } else {
            messageService.resourceMessageHandler(resourceIndex);
        }
    }

    public void collectResource(int resourceIndex, int amount) {
        this.setResource(resourceIndex, getResource(resourceIndex) + amount);
        messageService.resourceMessageHandler(resourceIndex, amount);
        messageService.stockpileInfo(getAllResources());
    }

    private boolean isAvailable(int currentValue, int useValue) {
        return currentValue >= useValue;
    }
}
