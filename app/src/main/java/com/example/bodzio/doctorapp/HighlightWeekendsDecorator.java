package com.example.bodzio.doctorapp;

        import android.annotation.SuppressLint;
        import android.graphics.Color;
        import android.graphics.Typeface;
        import android.graphics.drawable.ColorDrawable;
        import android.graphics.drawable.Drawable;
        import android.text.style.ForegroundColorSpan;
        import android.text.style.StyleSpan;

        import com.prolificinteractive.materialcalendarview.CalendarDay;
        import com.prolificinteractive.materialcalendarview.DayViewDecorator;
        import com.prolificinteractive.materialcalendarview.DayViewFacade;
        import java.util.Calendar;

        import static android.app.PendingIntent.getActivity;


public class HighlightWeekendsDecorator implements DayViewDecorator {

    private final Calendar calendar = Calendar.getInstance();
    //private final Drawable highlightDrawable;
    private static final int color = Color.parseColor("#878484");

    public HighlightWeekendsDecorator() {
       // highlightDrawable = new ColorDrawable(color);
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        day.copyTo(calendar);
        int weekDay = calendar.get(Calendar.DAY_OF_WEEK);
        return weekDay == Calendar.SATURDAY || weekDay == Calendar.SUNDAY;
    }


    public void decorate(DayViewFacade view) {
        view.addSpan(new StyleSpan(Typeface.BOLD));
        view.addSpan(new ForegroundColorSpan(color));
    }
}