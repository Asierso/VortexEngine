package com.asierso.vortexengine.window;

import com.asierso.vortexengine.miscellaneous.Dimension;
import org.jsfml.window.*;
import org.jsfml.graphics.*;
import org.jsfml.system.Clock;
import org.jsfml.window.event.Event;

/**
 * This class is the GameWindow base class. 
 * Contains the SFML base window and load the scenes
 *
 * @author Asierso
 */
public class Window {

    //Private window data
    private int width = 0;
    private int height = 0;
    private int frames = 60;
    private float fps = 0;
    private String title = "Window";
    private Color background = Color.BLACK;
    private ConstView windowView;

    //SFML window instance
    private RenderWindow render;

    //Container of runtime methods
    private BaseScene runtime;

    //Main constructors
    public Window() {
    }

    public Window(int width, int height) {
        setSize(width, height);
    }

    //Field setters
    /**
     * Set window size
     *
     * @param width Horizontal size
     * @param height Vertical size
     */
    public void setSize(int width, int height) {
        this.width = width;
        this.height = height;
    }

    /**
     * Get current window dimension
     *
     * @return Dimension of window
     */
    public Dimension getSize() {
        return new Dimension(width, height);
    }

    /**
     * Set window frame rate
     *
     * @param frames Number of frames to render in a second
     */
    public void setFrameRate(int frames) {
        this.frames = frames;
    }

    /**
     * Get frames renderer in 1 second
     *
     * @return FPS value
     */
    public float getFramesPerSecond() {
        return fps;
    }

    /**
     * Set runtime instructions when window executes
     *
     * @param runtime Runtime class
     */
    public void setRenderRuntime(BaseScene runtime) {
        this.runtime = runtime;
    }

    /**
     * Get RenderWindow class (SFML component)
     *
     * @return The SFML base window
     */
    public RenderWindow getRender() {
        return render;
    }

    /**
     * Set top title of window
     *
     * @param title String title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Set default background color when window refresh
     *
     * @param color Background color
     */
    public void setBackground(Color color) {
        this.background = color;
    }

    /**
     * Instantiate SFML window
     */
    public void instantiate() {
        if (runtime == null) {
            throw new NullPointerException();
        }
        //Create render instance
        render = new RenderWindow(new VideoMode(width, height), title);
        render.setVisible(true);
        reloadConfigs();

        //Startup execution
        runtime.start();

        //Save default view
        windowView = render.getDefaultView();

        //Initialize FPS counter
        Clock frameClock = new Clock();
        while (render.isOpen()) {
            reloadConfigs();
            //Refresh window and execute update method
            render.clear(background);
            Iterable<Event> events = render.pollEvents();
            runtime.update(this, events);

            //Detects close event
            events.forEach(obj -> {
                switch (obj.type) {
                    case CLOSED:
                        runtime.close();
                        render.close();
                        break;
                    case RESIZED:
                        ((View) windowView).setSize(width, height);
                        render.setView(windowView);
                        break;
                }
            });

            //Display
            render.display();
            float currentTime = frameClock.restart().asSeconds();
            fps = 1.f / currentTime;
        }
    }

    /**
     * Reload SFML configs with the set ones (even in main-loop)
     */
    private void reloadConfigs() {
        render.setFramerateLimit(frames);
        render.setTitle(title);
    }
}
