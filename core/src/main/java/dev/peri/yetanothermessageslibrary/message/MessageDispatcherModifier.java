package dev.peri.yetanothermessageslibrary.message;

/**
 * Modifier for a {@link MessageDispatcher} instance. Allowing to reuse dispatcher configurations.
 * 
 * @param <RECEIVER>
 * @param <DISPATCHER>
 */
public interface MessageDispatcherModifier<RECEIVER, DISPATCHER extends MessageDispatcher<RECEIVER, ? extends DISPATCHER>> {
    
    void modify(DISPATCHER dispatcher);
}
