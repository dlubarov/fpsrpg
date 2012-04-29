package msg.c2s;

import math.Vector3;
import ser.*;

// A new account request.

public class MotionRequestMessage extends ClientMessage {
    public final Vector3 position;

    public MotionRequestMessage(Vector3 position) {
        super(MySerializer.singleton);
        this.position = position;
    }

    @Override
    public String toString() {
        return String.format("MotionRequest(%s)", position);
    }

    public static class MySerializer extends Serializer<MotionRequestMessage> {
        public static final MySerializer singleton = new MySerializer();
        private MySerializer() {}

        @Override
        public void serialize(MotionRequestMessage msg, ByteSink sink) {
            Vector3Serializer.singleton.serialize(msg.position, sink);
        }

        @Override
        public MotionRequestMessage deserialize(ByteSource source) {
            Vector3 position = Vector3Serializer.singleton.deserialize(source);
            return new MotionRequestMessage(position);
        }
    }
}
