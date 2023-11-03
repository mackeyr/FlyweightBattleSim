import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.animation.AnimationTimer;
import java.util.ArrayList;
import java.util.List;

public class BallGravityCollisionApp extends Application {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private static final double BALL_RADIUS = 150;
    private static final double GRAVITY = 0.5;
    private static final double FRICTION = 0.99;
    private static final int NUM_BALLS = 1;
    private static final int GRID_SIZE = 10;
    private static final int MAX_VELOCITY = 20;

    private List<Ball> balls = new ArrayList<>();
    private boolean dragging = false;
    private Ball draggedBall = null;

    // Create a 2D grid to represent the playing area
    private int numRows = HEIGHT / GRID_SIZE;
    private int numCols = WIDTH / GRID_SIZE;
    private boolean[][] grid = new boolean[numRows][numCols];

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        canvas.setOnMousePressed(this::handleMousePressed);
        canvas.setOnMouseReleased(this::handleMouseReleased);
        canvas.setOnMouseDragged(this::handleMouseDragged);
        canvas.setOnMousePressed(this::handleKeyPress);

        StackPane root = new StackPane(canvas);

        Scene scene = new Scene(root, WIDTH, HEIGHT, Color.BLACK);
        primaryStage.setTitle("Ball Gravity and Collision");
        primaryStage.setScene(scene);
        primaryStage.show();

        for (int i = 0; i < NUM_BALLS; i++) {
            balls.add(new Ball(WIDTH / 2, HEIGHT / 2, 10, 10, BALL_RADIUS));
        }

        AnimationTimer animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                update();
                draw(gc);
            }
        };
        animationTimer.start();
    }

    private void handleMousePressed(MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY) {
            double mouseX = event.getX();
            double mouseY = event.getY();
            for (Ball ball : balls) {
                if (isMouseOverBall(mouseX, mouseY, ball)) {
                    dragging = true;
                    draggedBall = ball; // Store the dragged ball
                    draggedBall.setVelocityX(0);
                    draggedBall.setVelocityY(0);
                }
            }
        }
    }
    private boolean isMouseOverBall(double mouseX, double mouseY, Ball ball) {
        double dx = ball.getX() - mouseX;
        double dy = ball.getY() - mouseY;
        double distance = Math.sqrt(dx * dx + dy * dy);
        return distance < ball.getRadius();
    }


    private void handleMouseReleased(MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY) {
            dragging = false;
            draggedBall = null; // Clear the dragged ball
        }
    }

    private void handleKeyPress(MouseEvent event) {
        if (event.getButton() == MouseButton.SECONDARY) {
            List<Ball> removeList = new ArrayList<>();
            List<Ball> addList = new ArrayList<>();
            for (Ball ball : balls) {
                addList.addAll(ball.split());
                removeList.add(ball);
            }
            balls.addAll(addList);
            balls.removeAll(removeList);
        }

    }

    private void handleMouseDragged(MouseEvent event) {
        if (dragging && draggedBall != null) {
            draggedBall.setX(event.getX());
            draggedBall.setY(event.getY());
        }
    }

    private void update() {
        // Clear the grid
        clearGrid();

        for (Ball ball : balls) {
            // Apply gravity
            ball.setVelocityY(ball.getVelocityY() + GRAVITY);

            // Apply friction
            ball.setVelocityX(ball.getVelocityX() * FRICTION);
            ball.setVelocityY(ball.getVelocityY() * FRICTION);

            // Update ball position
            ball.setX(ball.getX() + ball.getVelocityX());
            ball.setY(ball.getY() + ball.getVelocityY());

            // Handle collisions with the sides
            handleSideCollisions(ball);

            // Check for collisions with other balls using the grid
            handleBallCollisions(ball);

            // Update the grid with the ball's position
            int row = (int) (ball.getY() / GRID_SIZE);
            int col = (int) (ball.getX() / GRID_SIZE);
            grid[row][col] = true;
        }
    }

    private void clearGrid() {
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                grid[i][j] = false;
            }
        }
    }

    private void handleSideCollisions(Ball ball) {
        if (ball.getX() < ball.getRadius()) {
            ball.setVelocityX(-ball.getVelocityX()/2);
            ball.setX(ball.getRadius());
        }
        if (ball.getX() > WIDTH - ball.getRadius()){
            ball.setVelocityX(-ball.getVelocityX()/2);
            ball.setX(WIDTH - (ball.getRadius()));
        }
        if (ball.getY() < ball.getRadius()) {
            ball.setVelocityY(-ball.getVelocityY()/2);
            ball.setY(ball.getRadius());
        }
        if (ball.getY() > HEIGHT - ball.getRadius()){
            ball.setVelocityY(-ball.getVelocityY()/2);
            ball.setY(HEIGHT - (ball.getRadius()));
        }
    }

    private void handleBallCollisions(Ball ball) {
        int col = (int) (ball.getX() / GRID_SIZE);
        int row = (int) (ball.getY() / GRID_SIZE);

        int startCol = Math.max(0, col);
        int endCol = Math.min(numCols - 1, col);
        int startRow = Math.max(0, row);
        int endRow = Math.min(numRows - 1, row);

        for (int i = startRow; i <= endRow; i++) {
            for (int j = startCol; j <= endCol; j++) {
                if (grid[i][j]) {
                    // Check for collision with balls in the same grid cell
                    for (Ball otherBall : balls) {
                        if (otherBall != ball) {
                            if (isColliding(ball, otherBall)) {
                                resolveCollision(ball, otherBall);
                            }
                        }
                    }
                }
            }
        }
    }

    private boolean isColliding(Ball ball1, Ball ball2) {
        double dx = ball1.getX() - ball2.getX();
        double dy = ball1.getY() - ball2.getY();
        double distance = Math.sqrt(dx * dx + dy * dy);
        return distance < ball1.getRadius() + ball2.getRadius();
    }

    private void resolveCollision(Ball ball1, Ball ball2) {
        double dx = ball2.getX() - ball1.getX();
        double dy = ball2.getY() - ball1.getY();
        double distance = Math.sqrt(dx * dx + dy * dy);
        double normalX = dx / distance;
        double normalY = dy / distance;

        // Calculate relative velocity
        double relativeVelocityX = ball2.getVelocityX() - ball1.getVelocityX();
        double relativeVelocityY = ball2.getVelocityY() - ball1.getVelocityY();

        // Calculate relative velocity in terms of the normal direction
        double relativeSpeed = relativeVelocityX * normalX + relativeVelocityY * normalY;

        // Check if balls are moving toward each other
        if (relativeSpeed < 0) {
            // Calculate impulse (change in velocity)
            double impulse = (2 * relativeSpeed);

            double newVelocityX1 = ball1.getVelocityX() - impulse * normalX;
            double newVelocityY1 = ball1.getVelocityY() - impulse * normalY;
            double newVelocityX2 = ball2.getVelocityX() + impulse * normalX;
            double newVelocityY2 = ball2.getVelocityY() + impulse * normalY;

            double newSpeed1 = Math.sqrt(newVelocityX1 * newVelocityX1 + newVelocityY1 * newVelocityY1);
            double newSpeed2 = Math.sqrt(newVelocityX2 * newVelocityX2 + newVelocityY2 * newVelocityY2);

            if (newSpeed1 > MAX_VELOCITY) {
                // Adjust the velocity to the maximum
                double scale = MAX_VELOCITY / newSpeed1;
                newVelocityX1 *= scale;
                newVelocityY1 *= scale;
            }

            if (newSpeed2 > MAX_VELOCITY) {
                // Adjust the velocity to the maximum
                double scale = MAX_VELOCITY / newSpeed2;
                newVelocityX2 *= scale;
                newVelocityY2 *= scale;
            }

            ball1.setVelocityX(newVelocityX1);
            ball1.setVelocityY(newVelocityY1);
            ball2.setVelocityX(newVelocityX2);
            ball2.setVelocityY(newVelocityY2);
        }
    }

    private void draw(GraphicsContext gc) {
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, WIDTH, HEIGHT);

        gc.setFill(Color.WHITE);
        for (Ball ball : balls) {
            gc.fillOval(ball.getX() - ball.getRadius(), ball.getY() - ball.getRadius(),
                    2 * ball.getRadius(), 2 * ball.getRadius());
        }
    }
}
