import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;

public class AnimatedNeonButton extends JButton {

    private boolean isRunning = false;
    private float neonOffset = 0; // زاوية دوران النيون
    private Timer animationTimer;
    private float hue = 0; // للتحكم بتغير الألوان المتتابع (Rainbow)

    public AnimatedNeonButton() {
        setText("Start here");
        setFont(new Font("Segoe UI", Font.BOLD, 28));
        setForeground(Color.WHITE);
        setContentAreaFilled(false);
        setBorderPainted(false);
        setFocusPainted(false);
        setCursor(new Cursor(Cursor.HAND_CURSOR));

        // محرك الأنميشن (يحدث الشاشة كل 20 ملي ثانية لتأثير ناعم)
        animationTimer = new Timer(20, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isRunning) {
                    neonOffset += 0.05f; // سرعة دوران النيون
                    hue += 0.01f;        // سرعة تغير الألوان
                    if (hue > 1) hue = 0;
                    repaint();
                }
            }
        });

        // الاستماع لحدث الضغط (Click) لتغيير الحالة
        addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isRunning = !isRunning;
                if (isRunning) {
                    setText("Stop");
                    animationTimer.start();
                } else {
                    setText("Start here");
                    animationTimer.stop();
                    repaint(); // إعادة الرسم لإخفاء تأثير النيون
                }
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int width = getWidth();
        int height = getHeight();
        int padding = 6; // مساحة لظهور إضاءة النيون خارج الزر الأساسي

        int btnWidth = width - (padding * 2);
        int btnHeight = height - (padding * 2);

        // 1. رسم تأثير إضاءة النيون الدائرية (تظهر فقط عند حالة التشغيل)
        if (isRunning) {
            g2d.setStroke(new BasicStroke(5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            
            // إنتاج لون نيون متغير باستمرار بناءً على قيمة hue
            Color neonColor = Color.getHSBColor(hue, 0.8f, 1.0f);
            
            // دمج اللون المتغير مع تأثير حركة الدوران باستخدام GradientPaint متقلب
            float x1 = (float) (width / 2 + Math.cos(neonOffset) * width);
            float y1 = (float) (height / 2 + Math.sin(neonOffset) * height);
            float x2 = (float) (width / 2 - Math.cos(neonOffset) * width);
            float y2 = (float) (height / 2 - Math.sin(neonOffset) * height);
            
            GradientPaint neonGradient = new GradientPaint(x1, y1, neonColor, x2, y2, new Color(0,0,0,0));
            g2d.setPaint(neonGradient);
            
            // رسم خط النيون الخارجي حول الزر
            g2d.draw(new RoundRectangle2D.Float(padding - 2, padding - 2, btnWidth + 4, btnHeight + 4, btnHeight, btnHeight));
        }

        // 2. رسم جسم الزر الزرق المتدرج
        GradientPaint bodyGradient = new GradientPaint(
                0, padding, new Color(28, 110, 204),
                0, padding + btnHeight, new Color(12, 77, 162)
        );
        g2d.setPaint(bodyGradient);
        g2d.fill(new RoundRectangle2D.Float(padding, padding, btnWidth, btnHeight, btnHeight, btnHeight));

        // 3. رسم الدائرة اليمنى
        int circleRadius = (int) (btnHeight * 0.9);
        int circleX = padding + btnWidth - circleRadius - (btnHeight - circleRadius) / 2;
        int circleY = padding + (btnHeight - circleRadius) / 2;

        GradientPaint circleGradient = new GradientPaint(
                circleX, circleY, new Color(38, 126, 226, 180),
                circleX, circleY + circleRadius, new Color(8, 62, 136, 180)
        );
        g2d.setPaint(circleGradient);
        g2d.fillOval(circleX, circleY, circleRadius, circleRadius);

        // 4. رسم الأيقونة الداخلية (مثلث أبيض للتشغيل، أو مربع أحمر/فاتح للإيقاف)
        int iconSize = circleRadius / 3;
        int iconX = circleX + (circleRadius - iconSize) / 2;
        int iconY = circleY + (circleRadius - iconSize) / 2;

        if (!isRunning) {
            // رسم سهم Start
            g2d.setColor(new Color(240, 244, 250));
            int[] xPoints = {iconX + 3, iconX + 3, iconX + 3 + iconSize};
            int[] yPoints = {iconY, iconY + iconSize, iconY + (iconSize / 2)};
            g2d.fillPolygon(xPoints, yPoints, 3);
        } else {
            // رسم مربع Stop
            g2d.setColor(new Color(255, 100, 100)); // لون أحمر نيون خفيف للإيقاف
            g2d.fillRect(iconX + 2, iconY + 2, iconSize - 4, iconSize - 4);
        }

        // 5. رسم النص الحالي (Start here أو Stop)
        FontMetrics fm = g2d.getFontMetrics();
        int textX = padding + (btnWidth - circleRadius - fm.stringWidth(getText())) / 2 + 10;
        int textY = padding + (btnHeight - fm.getHeight()) / 2 + fm.getAscent();
        
        g2d.setColor(Color.WHITE);
        g2d.drawString(getText(), textX, textY);

        g2d.dispose();
    }

    // تشغيل المعاينة
    public static void main(String[] args) {
        JFrame frame = new JFrame("Neon Start/Stop Button");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 250);
        frame.setLayout(new GridBagLayout());
        frame.getContentPane().setBackground(new Color(20, 20, 25)); // خلفية داكنة لتبرز إضاءة النيون الملونة

        AnimatedNeonButton neonButton = new AnimatedNeonButton();
        neonButton.setPreferredSize(new Dimension(400, 110)); // حجم أكبر قليلاً لاستيعاب مساحة النيون

        frame.add(neonButton);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
