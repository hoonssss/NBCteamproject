package src;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MessageBox{
    private List<String> messages;
    public MessageBox(){
        messages = new ArrayList<>();
    }
    public List<String> getMessage(){
        return messages;
    }
    public void addMessage(String product){
        messages.add(product);
    }
    public void clearMessage(){
        messages.clear();
    }
}
