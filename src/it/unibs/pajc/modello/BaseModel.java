package it.unibs.pajc.modello;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.EventListenerList;

public class BaseModel {
  private EventListenerList listenerList = new EventListenerList();
  private EventListenerList pescaList = new EventListenerList();

  public void addChangeListener(ChangeListener l) {
    listenerList.add(ChangeListener.class, l);
  }
  
  public void addPescListener(ChangeListener l){pescaList.add(ChangeListener.class, l);}

  public void removeChangeListener(ChangeListener l) {
    listenerList.remove(ChangeListener.class, l);
  }

  public void pescaChange(){
    pescaChange(new ChangeEvent(this));
  }
  
  public void fireValuesChange() {
    fireValuesChange(new ChangeEvent(this));
  }
  
  public void pescaChange(ChangeEvent changeEvent) {
    Object[] listeners = listenerList.getListenerList();
    
    for (int i = listeners.length - 2; i >= 0; i -= 2) {
      if (listeners[i] == ChangeListener.class)
        ((ChangeListener) listeners[i + 1]).stateChanged(changeEvent);
    }
  }

  public void fireValuesChange(ChangeEvent changeEvent) {
    Object[] listeners = listenerList.getListenerList();

    for (int i = listeners.length - 2; i >= 0; i -= 2) {
      if (listeners[i] == ChangeListener.class)
        ((ChangeListener) listeners[i + 1]).stateChanged(changeEvent);
    }
  }
  
  
}
