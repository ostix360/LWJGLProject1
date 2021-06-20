package fr.ostix.game.menu.component;

import fr.ostix.game.core.Input;
import fr.ostix.game.toolBox.Color;
import org.lwjgl.glfw.GLFW;

public class Button extends Component {

    private boolean pressed;

    public Button(float x, float y, float width, float height, int texture) {
        super(x, y, width, height, texture);
        this.texture.setLayer(new Color(0.45f, 0.45f, 0.5f, 0.85f));
    }

    @Override
    public void update() {
        float mX = (float) Input.getMouseX();
        float mY = (float) Input.getMouseY();


        pressed = mX >= this.x && mY >= this.y + height / 2 &&
                mX < (this.x + this.width) && mY < (this.y + this.height + height / 2) && Input.keysMouse[GLFW.GLFW_MOUSE_BUTTON_1];
        this.texture.hasLayer(isPressed());
    }

    public boolean isPressed() {
        return pressed;
    }

}
