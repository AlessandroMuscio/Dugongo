package it.unibs.pajc.model;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.EventListenerList;

public class BaseModel {
  private EventListenerList listenerList = new EventListenerList();

  public void addChangeListener(ChangeListener l) {
    listenerList.add(ChangeListener.class, l);
  }

  public void removeChangeListener(ChangeListener l) {
    listenerList.remove(ChangeListener.class, l);
  }

  public void fireValuesChange() {
    fireValuesChange(new ChangeEvent(this));
  }

  public void fireValuesChange(ChangeEvent changeEvent) {
    Object[] listeners = listenerList.getListenerList();

    for (int i = listeners.length - 2; i >= 0; i -= 2) {
      if (listeners[i] == ChangeListener.class)
        ((ChangeListener) listeners[i + 1]).stateChanged(changeEvent);
    }
  }
}
