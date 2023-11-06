import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.animation.AnimationTimer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Main extends Application {
    private Group BallView;
    private Pane pane;
    private Label memory;
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private static final double BALL_RADIUS = 150;
    private static final double GRAVITY = 0.5;
    private static final double FRICTION = 0.99;
    private static final int NUM_BALLS = 2;
    private static final int GRID_SIZE = 5;
    private static final int MAX_VELOCITY = 20;
    private int generation;
    private Image image = new Image("Table_tennis_ball.png");
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
        BallView = new Group();
        pane = new Pane();

        pane.setOnMousePressed(this::handleMousePressed);
        pane.setOnMouseReleased(this::handleMouseReleased);
        pane.setOnMouseDragged(this::handleMouseDragged);
        pane.setOnMousePressed(this::handleKeyPress);
        memory = new Label("Memory Usage: " + (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
        memory.setTextFill(Color.WHITE);
        memory.setFont(new Font("Arial", 24));

        pane.getChildren().add(BallView);
        pane.getChildren().add(memory);
        Scene scene = new Scene(pane, WIDTH, HEIGHT, Color.BLACK);
        pane.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        primaryStage.setTitle("Ball Gravity and Collision");
        primaryStage.setScene(scene);
        primaryStage.show();

        for (int i = 0; i < NUM_BALLS; i++) {
            balls.add(createBall(WIDTH / 2, HEIGHT / 2, 10, 10, "father", image, BALL_RADIUS));
        }

        ExecutorService executor = Executors.newFixedThreadPool(NUM_BALLS);
        AnimationTimer animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                clearGrid();
                update(executor);
                draw();
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
        return distance < ball.getType().getRadius();
    }


    private void handleMouseReleased(MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY) {
            dragging = false;
            draggedBall = null; // Clear the dragged ball
        }
    }

    private void handleKeyPress(MouseEvent event) {
        if (event.getButton() == MouseButton.SECONDARY) {
            split();
        }
    }

    private void handleMouseDragged(MouseEvent event) {
        if (dragging && draggedBall != null) {
            draggedBall.setX(event.getX());
            draggedBall.setY(event.getY());
        }
    }

    private void update(ExecutorService executor) {
        List<Future<Void>> futures = new ArrayList<>();

        for (Ball ball : balls) {
            Future<Void> future = executor.submit(() -> {
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
                return null;
            });

            futures.add(future);
        }
        for (Future<Void> future : futures) {
            try {
                future.get();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        memory.setText("Memory Usage: " + (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
    }

    private void clearGrid() {
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                grid[i][j] = false;
            }
        }
    }

    private void handleSideCollisions(Ball ball) {
        if (ball.getX() < ball.getType().getRadius()) {
            ball.setVelocityX(-ball.getVelocityX()/2);
            ball.setX(ball.getType().getRadius());
        }
        if (ball.getX() > WIDTH - ball.getType().getRadius()){
            ball.setVelocityX(-ball.getVelocityX()/2);
            ball.setX(WIDTH - (ball.getType().getRadius()));
        }
        if (ball.getY() < ball.getType().getRadius()) {
            ball.setVelocityY(-ball.getVelocityY()/2);
            ball.setY(ball.getType().getRadius());
        }
        if (ball.getY() > HEIGHT - ball.getType().getRadius()){
            ball.setVelocityY(-ball.getVelocityY()/2);
            ball.setY(HEIGHT - (ball.getType().getRadius()));
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
        return distance < ball1.getType().getRadius() + ball2.getType().getRadius();
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

    private void draw() {
        BallView.getChildren().clear(); // Clear the previous balls

        for (Ball ball : balls) {
            ImageView ballImage = new ImageView(ball.getType().getBallImage());
            ballImage.setFitWidth(2 * ball.getType().getRadius());
            ballImage.setFitHeight(2 * ball.getType().getRadius());
            ballImage.setLayoutX(ball.getX() - ball.getType().getRadius());
            ballImage.setLayoutY(ball.getY() - ball.getType().getRadius());
            BallView.getChildren().add(ballImage);
        }
    }
    private Ball createBall(double x, double y, double velocityX, double velocityY, String name, Image image, double radius){
        BallType type = BallFactory.getBallType(name, image, radius);
        Ball ball = new Ball(x, y, velocityX, velocityY, type);
        return ball;
    }
    private List<Ball> split(Ball ball) {
        List<Ball> balls = new ArrayList<>();
        double radius = ball.getType().getRadius() * 0.7;
        String name = "gen: " + generation;
        Ball newBall1 = createBall(ball.getX() - radius, ball.getY(), -ball.getVelocityX(), ball.getVelocityY(), name, image, radius);
        Ball newBall2 = createBall(ball.getX() + radius, ball.getY(), -ball.getVelocityX(), ball.getVelocityY(), name, image, radius);
        balls.add(newBall1);
        balls.add(newBall2);
        return balls;
    }
    private void split() {
        ++generation;
        ExecutorService executor = Executors.newFixedThreadPool(balls.size());
        List<Future<List<Ball>>> splitFutures = new ArrayList();

        for (Ball ball : balls) {
            Future<List<Ball>> future = executor.submit(() -> split(ball));
            splitFutures.add(future);
        }

        List<Ball> newBalls = new ArrayList();
        List<Ball> oldBallsToRemove = new ArrayList();

        for (int i = 0; i < splitFutures.size(); i++) {
            Future<List<Ball>> future = splitFutures.get(i);
            Ball originalBall = balls.get(i);

            try {
                List<Ball> splitResult = future.get();
                if (splitResult != null && !splitResult.isEmpty()) {
                    newBalls.addAll(splitResult);
                    oldBallsToRemove.add(originalBall);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // Remove the old balls
        balls.removeAll(oldBallsToRemove);

        // Add the new balls
        balls.addAll(newBalls);
    }
}
