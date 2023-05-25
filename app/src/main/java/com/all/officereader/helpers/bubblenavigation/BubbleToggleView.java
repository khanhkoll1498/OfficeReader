package com.all.officereader.helpers.bubblenavigation;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Build;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.all.officereader.R;
import com.all.officereader.helpers.bubblenavigation.util.ViewUtils;

/**
 * BubbleToggleView
 *
 * @author Gaurav Kumar
 */
public class BubbleToggleView extends LinearLayout {

    private static final String TAG = "BNI_View";
    private static final int DEFAULT_ANIM_DURATION = 300;

    private BubbleToggleItem bubbleToggleItem;

    private boolean isActive = false;

    private ImageView iconView;
    private TextView titleView;
    private Drawable icon = null;
    private Drawable iconInactive = null;
    private int mPos = 0;

    private int animationDuration;
    private boolean showShapeAlways;

    private float maxTitleWidth;
    private float measuredTitleWidth;

    /**
     * Constructors
     */
    public BubbleToggleView(Context context) {
        super(context);
        init(context, null);
    }

    public BubbleToggleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public BubbleToggleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public BubbleToggleView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    /////////////////////////////////////
    // PRIVATE METHODS
    /////////////////////////////////////

    /**
     * Initialize
     *
     * @param context current context
     * @param attrs   custom attributes
     */
    private void init(Context context, @Nullable AttributeSet attrs) {
        //initialize default component
        String title = "Title";

        Drawable shape = null;
        int shapeColor = Integer.MIN_VALUE;
        int colorActive = ViewUtils.getThemeAccentColor(context);
        int colorInactive = ContextCompat.getColor(context, R.color.default_inactive_color);
        float titleSize = context.getResources().getDimension(R.dimen.default_nav_item_text_size);
        maxTitleWidth = context.getResources().getDimension(R.dimen.default_nav_item_title_max_width);
        float iconWidth = context.getResources().getDimension(R.dimen.default_icon_size);
        float iconHeight = context.getResources().getDimension(R.dimen.default_icon_size);
        int internalPadding = (int) context.getResources().getDimension(R.dimen.default_nav_item_padding);
        int titlePadding = (int) context.getResources().getDimension(R.dimen.default_nav_item_text_padding);


        if (attrs != null) {
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.BubbleToggleView, 0, 0);
            try {
                mPos = ta.getInt(R.styleable.BubbleToggleView_position,0);
                icon = ta.getDrawable(R.styleable.BubbleToggleView_bt_icon);
                iconInactive = ta.getDrawable(R.styleable.BubbleToggleView_bt_icon_inactive);
                iconWidth = ta.getDimension(R.styleable.BubbleToggleView_bt_iconWidth, iconWidth);
                iconHeight = ta.getDimension(R.styleable.BubbleToggleView_bt_iconHeight, iconHeight);
                shape = ta.getDrawable(R.styleable.BubbleToggleView_bt_shape);
                shapeColor = ta.getColor(R.styleable.BubbleToggleView_bt_shapeColor, shapeColor);
                showShapeAlways = ta.getBoolean(R.styleable.BubbleToggleView_bt_showShapeAlways, false);
                title = ta.getString(R.styleable.BubbleToggleView_bt_title);
                titleSize = ta.getDimension(R.styleable.BubbleToggleView_bt_titleSize, titleSize);
                colorActive = ta.getColor(R.styleable.BubbleToggleView_bt_colorActive, colorActive);
                colorInactive = ta.getColor(R.styleable.BubbleToggleView_bt_colorInactive, colorInactive);
                isActive = ta.getBoolean(R.styleable.BubbleToggleView_bt_active, false);
                animationDuration = ta.getInteger(R.styleable.BubbleToggleView_bt_duration, DEFAULT_ANIM_DURATION);
                internalPadding = (int) ta.getDimension(R.styleable.BubbleToggleView_bt_padding, internalPadding);
                titlePadding = (int) ta.getDimension(R.styleable.BubbleToggleView_bt_titlePadding, titlePadding);
            } finally {
                ta.recycle();
            }
        }

        //set the default icon
        if (icon == null)
            icon = ContextCompat.getDrawable(context, R.drawable.default_icon);

        //set the default shape
        if (shape == null)
            shape = ContextCompat.getDrawable(context, R.drawable.transition_background_drawable);

        //create a default bubble item
        bubbleToggleItem = new BubbleToggleItem();
        bubbleToggleItem.setIcon(icon);
        bubbleToggleItem.setIconInactive(iconInactive);
        bubbleToggleItem.setShape(shape);
        bubbleToggleItem.setTitle(title);
        bubbleToggleItem.setTitleSize(titleSize);
        bubbleToggleItem.setTitlePadding(titlePadding);
        bubbleToggleItem.setShapeColor(shapeColor);
        bubbleToggleItem.setColorActive(colorActive);
        bubbleToggleItem.setColorInactive(colorInactive);
        bubbleToggleItem.setIconWidth(iconWidth);
        bubbleToggleItem.setIconHeight(iconHeight);
        bubbleToggleItem.setInternalPadding(internalPadding);

        //set the orientation
        setOrientation(HORIZONTAL);
        //set the gravity
        setGravity(Gravity.CENTER);
        //set the internal padding
        setPadding(
                bubbleToggleItem.getInternalPadding(),
                bubbleToggleItem.getInternalPadding(),
                bubbleToggleItem.getInternalPadding(),
                bubbleToggleItem.getInternalPadding());
        post(new Runnable() {
            @Override
            public void run() {
                //make sure the padding is added
                setPadding(
                        bubbleToggleItem.getInternalPadding(),
                        bubbleToggleItem.getInternalPadding(),
                        bubbleToggleItem.getInternalPadding(),
                        bubbleToggleItem.getInternalPadding());
            }
        });

        createBubbleItemView(context);
        setInitialState(isActive);
    }

    /**
     * Create the components of the bubble item view {@link #iconView} and {@link #titleView}
     *
     * @param context current context
     */
    private void createBubbleItemView(Context context) {

        //create the nav icon
        iconView = new ImageView(context);
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams((int) bubbleToggleItem.getIconWidth(), (int) bubbleToggleItem.getIconHeight());
        iconView.setLayoutParams(lp);
        iconView.setImageDrawable(bubbleToggleItem.getIcon());

        //create the nav title
        titleView = new TextView(context);
        titleView.setSingleLine(true);
        titleView.setTextColor(bubbleToggleItem.getColorActive());
        titleView.setText(bubbleToggleItem.getTitle());
        titleView.setTextSize(TypedValue.COMPLEX_UNIT_PX, bubbleToggleItem.getTitleSize());

        //get the current measured title width
        titleView.setVisibility(VISIBLE);
        //update the margin of the text view
        titleView.setPadding(bubbleToggleItem.getTitlePadding(), 0, bubbleToggleItem.getTitlePadding(), 0);
        //measure the content width
        titleView.measure(0, 0);       //must call measure!
        measuredTitleWidth = titleView.getMeasuredWidth();  //get width
        //limit measured width, based on the max width
        if (measuredTitleWidth > maxTitleWidth)
            measuredTitleWidth = maxTitleWidth;

        //change the visibility
        titleView.setVisibility(GONE);

        addView(iconView);
        addView(titleView);

        //set the initial state
        setInitialState(isActive);
    }

    /////////////////////////////////
    // PUBLIC METHODS
    ////////////////////////////////

    /**
     * Updates the Initial State
     *
     * @param isActive current state
     */
    public void setInitialState(boolean isActive) {
        //set the background
        setBackground(bubbleToggleItem.getShape());


        if (isActive) {
            if (mPos != 4){
                ViewUtils.updateDrawableColor(iconView.getDrawable(), bubbleToggleItem.getColorActive());
            }

            //ViewUtils.updateDrawable(iconView.getDrawable(), isActive);

            this.isActive = true;
            titleView.setVisibility(VISIBLE);


            if (getBackground() instanceof TransitionDrawable) {
                TransitionDrawable trans = (TransitionDrawable) getBackground();
                trans.startTransition(0);
            } else {
                if (!showShapeAlways && bubbleToggleItem.getShapeColor() != Integer.MIN_VALUE)
                    ViewUtils.updateDrawableColor(bubbleToggleItem.getShape(), bubbleToggleItem.getShapeColor());
            }



        } else {
            if (mPos != 4) {
                 ViewUtils.updateDrawableColor(iconView.getDrawable(), bubbleToggleItem.getColorInactive());
            }
            icon = ContextCompat.getDrawable(getContext(), R.drawable.ic_gift_main);
            bubbleToggleItem.setIcon(icon);

            this.isActive = false;
            titleView.setVisibility(GONE);
            if (!showShapeAlways) {
                if (!(getBackground() instanceof TransitionDrawable)) {
                    setBackground(null);
                }
            }

        }
    }

    /**
     * Toggles between Active and Inactive state
     */
    public void toggle() {
        if (!isActive)
            activate();
        else
            deactivate();
    }

    /**
     * Set Active state
     */
    public void activate() {
        if (mPos != 4) {
            ViewUtils.updateDrawableColor(iconView.getDrawable(), bubbleToggleItem.getColorActive());
        }else {
            iconView.setImageDrawable(getResources().getDrawable(R.drawable.ic_gift_main_selected_2));
        }

        isActive = true;
        titleView.setVisibility(VISIBLE);
        ValueAnimator animator = ValueAnimator.ofFloat(0f, 1f);
        animator.setDuration(animationDuration);
        animator.addUpdateListener(animation -> {
            float value = (float) animation.getAnimatedValue();
            titleView.setWidth((int) (measuredTitleWidth * value));
            //end of animation
            if (value >= 1.0f) {
                //do something
            }
        });
        animator.start();

        if (getBackground() instanceof TransitionDrawable) {
            TransitionDrawable trans = (TransitionDrawable) getBackground();
            trans.startTransition(animationDuration);
        } else {
            //if not showing Shape Always and valid shape color present, use that as tint
            if (!showShapeAlways && bubbleToggleItem.getShapeColor() != Integer.MIN_VALUE)
                ViewUtils.updateDrawableColor(bubbleToggleItem.getShape(), bubbleToggleItem.getShapeColor());
            setBackground(bubbleToggleItem.getShape());
        }


    }

    /**
     * Set Inactive State
     */
    public void deactivate() {
        if (mPos !=4 ){
            ViewUtils.updateDrawableColor(iconView.getDrawable(), bubbleToggleItem.getColorInactive());
        }else {
            iconView.setImageDrawable(getResources().getDrawable(R.drawable.ic_gift_main_2));
        }

//        icon = ContextCompat.getDrawable(getContext(), R.drawable.default_icon);
//
//        bubbleToggleItem.setIcon(icon);

        isActive = false;
        ValueAnimator animator = ValueAnimator.ofFloat(1f, 0f);
        animator.setDuration(animationDuration);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                titleView.setWidth((int) (measuredTitleWidth * value));
                //end of animation
                if (value <= 0.0f)
                    titleView.setVisibility(GONE);
            }
        });
        animator.start();

        if (getBackground() instanceof TransitionDrawable) {
            TransitionDrawable trans = (TransitionDrawable) getBackground();
            trans.reverseTransition(animationDuration);
        } else {
            if (!showShapeAlways) setBackground(null);
        }

    }

    /**
     * Get the current state of the view
     *
     * @return the current state
     */
    public boolean isActive() {
        return isActive;
    }

    /**
     * Sets the {@link Typeface} of the {@link #titleView}
     *
     * @param typeface to be used
     */
    public void setTitleTypeface(Typeface typeface) {
        titleView.setTypeface(typeface);
    }

    /**
     * Updates the measurements and fits the view
     *
     * @param maxWidth in pixels
     */
    public void updateMeasurements(int maxWidth) {
        int marginLeft = 0, marginRight = 0;
        ViewGroup.LayoutParams titleViewLayoutParams = titleView.getLayoutParams();
        if (titleViewLayoutParams instanceof LayoutParams) {
            marginLeft = ((LayoutParams) titleViewLayoutParams).rightMargin;
            marginRight = ((LayoutParams) titleViewLayoutParams).leftMargin;
        }

        int newTitleWidth = maxWidth
                - (getPaddingRight() + getPaddingLeft())
                - (marginLeft + marginRight)
                - ((int) bubbleToggleItem.getIconWidth())
                + titleView.getPaddingRight() + titleView.getPaddingLeft();

        //if the new calculate title width is less than current one, update the titleView specs
        if (newTitleWidth > 0 && newTitleWidth < measuredTitleWidth) {
            measuredTitleWidth = titleView.getMeasuredWidth();
        }
    }

}
