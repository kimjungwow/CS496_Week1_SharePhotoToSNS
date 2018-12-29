package com.example.myapplication;

import android.animation.Animator;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageView;

import net.alhazmy13.imagefilter.ImageFilter;

public class ZoomActivity extends FragmentActivity {
    // Hold a reference to the current animator,
    // so that it can be canceled mid-way.
    private Animator mCurrentAnimator;

    // The system "short" animation time duration, in milliseconds. This
    // duration is ideal for subtle animations or animations that occur
    // very frequently.
    private int mShortAnimationDuration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zoom);
        final int[] img = {R.drawable.a, R.drawable.b, R.drawable.c, R.drawable.d, R.drawable.e, R.drawable.f, R.drawable.g, R.drawable.h,
                R.drawable.i, R.drawable.j, R.drawable.k, R.drawable.l, R.drawable.m, R.drawable.n, R.drawable.o,
                R.drawable.p, R.drawable.q, R.drawable.r, R.drawable.s, R.drawable.t, R.drawable.u, R.drawable.v
        };

        // Hook up clicks on the thumbnail views.
        Intent intent = getIntent();
        final int position = intent.getIntExtra("index", 0);
        final ImageView zoomview = findViewById(R.id.expanded_image);
        zoomview.setImageResource(img[position]);


        final View fb = findViewById(R.id.filterbutton);
        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                zoomview.invalidate();
                BitmapDrawable drawable = (BitmapDrawable) zoomview.getDrawable();
                Bitmap bitmap = drawable.getBitmap();
               Bitmap next = ImageFilter.applyFilter(bitmap, ImageFilter.Filter.GRAY);

                zoomview.setImageDrawable(null);
                ImageView filterview = findViewById(R.id.filtered_image);
//                filterview.setImageResource(img[position+1]);
                filterview.setImageBitmap(next);
            }

        });

        final View thumb1View = findViewById(R.id.thumb_button_1);
        thumb1View.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                zoomImageFromThumb(thumb1View, R.drawable.image1);
//                Toast.makeText(getApplicationContext(), position + " 번째 사진", Toast.LENGTH_SHORT).show();

                onBackPressed(); // Go back to previous fragment!
            }
        });

        // Retrieve and cache the system's default "short" animation time.
        mShortAnimationDuration = getResources().getInteger(
                android.R.integer.config_shortAnimTime);
    }

}









/*
public class ZoomActivity extends Fragment {


    public static ZoomActivity newInstance() {
        Bundle args = new Bundle();
        ZoomActivity za = new ZoomActivity();
        za.setArguments(args);
        return za;
    }
    public ZoomActivity() {
        // Required empty public constructor
    }
    // Hold a reference to the current animator,
    // so that it can be canceled mid-way.
    private Animator mCurrentAnimator;

    // The system "short" animation time duration, in milliseconds. This
    // duration is ideal for subtle animations or animations that occur
    // very frequently.
    private int mShortAnimationDuration;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        getActivity().setContentView(R.layout.activity_zoom);
        View thumb1View = inflater.inflate(R.layout.tab2images, container, false);

        // Hook up clicks on the thumbnail views.
        int[] img = {R.drawable.a, R.drawable.b, R.drawable.c, R.drawable.d, R.drawable.e, R.drawable.f, R.drawable.g, R.drawable.h,
                R.drawable.i, R.drawable.j, R.drawable.k, R.drawable.l, R.drawable.m, R.drawable.n, R.drawable.o,
                R.drawable.p, R.drawable.q, R.drawable.r, R.drawable.s, R.drawable.t, R.drawable.u, R.drawable.v
        };
        Intent intent = getActivity().getIntent();
        final int position = intent.getIntExtra("index",0);
        final ImageView zoomview = getActivity().findViewById(R.id.expanded_image);
        zoomview.setImageResource(img[position]);



//        final View thumb1View = getActivity().findViewById(R.id.thumb_button_1);
        thumb1View.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                zoomImageFromThumb(thumb1View, R.drawable.c);
                Toast.makeText(getActivity().getApplicationContext(), position + " 번째 사진", Toast.LENGTH_SHORT).show();
//                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//                fragmentManager.beginTransaction().remove(ZoomActivity.this).commit();
//                fragmentManager.popBackStackImmediate();
            }
        });

        // Retrieve and cache the system's default "short" animation time.
        mShortAnimationDuration = getResources().getInteger(android.R.integer.config_shortAnimTime);
        return thumb1View;
    }
*/


//
//    private void zoomImageFromThumb(final View thumbView, int imageResId) {
//        // If there's an animation in progress, cancel it
//        // immediately and proceed with this one.
//        if (mCurrentAnimator != null) {
//            mCurrentAnimator.cancel();
//        }
//
//        // Load the high-resolution "zoomed-in" image.
//        final ImageView expandedImageView = (ImageView) getActivity().findViewById(R.id.expanded_image);
//        expandedImageView.setImageResource(imageResId);
//
//        // Calculate the starting and ending bounds for the zoomed-in image.
//        // This step involves lots of math. Yay, math.
//        final Rect startBounds = new Rect();
//        final Rect finalBounds = new Rect();
//        final Point globalOffset = new Point();
//
//        // The start bounds are the global visible rectangle of the thumbnail,
//        // and the final bounds are the global visible rectangle of the container
//        // view. Also set the container view's offset as the origin for the
//        // bounds, since that's the origin for the positioning animation
//        // properties (X, Y).
//        thumbView.getGlobalVisibleRect(startBounds);
//        getActivity().findViewById(R.id.container).getGlobalVisibleRect(finalBounds, globalOffset);
//        startBounds.offset(-globalOffset.x, -globalOffset.y);
//        finalBounds.offset(-globalOffset.x, -globalOffset.y);
//
//        // Adjust the start bounds to be the same aspect ratio as the final
//        // bounds using the "center crop" technique. This prevents undesirable
//        // stretching during the animation. Also calculate the start scaling
//        // factor (the end scaling factor is always 1.0).
//        float startScale;
//        if ((float) finalBounds.width() / finalBounds.height()
//                > (float) startBounds.width() / startBounds.height()) {
//            // Extend start bounds horizontally
//            startScale = (float) startBounds.height() / finalBounds.height();
//            float startWidth = startScale * finalBounds.width();
//            float deltaWidth = (startWidth - startBounds.width()) / 2;
//            startBounds.left -= deltaWidth;
//            startBounds.right += deltaWidth;
//        } else {
//            // Extend start bounds vertically
//            startScale = (float) startBounds.width() / finalBounds.width();
//            float startHeight = startScale * finalBounds.height();
//            float deltaHeight = (startHeight - startBounds.height()) / 2;
//            startBounds.top -= deltaHeight;
//            startBounds.bottom += deltaHeight;
//        }
//
//        // Hide the thumbnail and show the zoomed-in view. When the animation
//        // begins, it will position the zoomed-in view in the place of the
//        // thumbnail.
//        thumbView.setAlpha(0f);
//        expandedImageView.setVisibility(View.VISIBLE);
//
//        // Set the pivot point for SCALE_X and SCALE_Y transformations
//        // to the top-left corner of the zoomed-in view (the default
//        // is the center of the view).
//        expandedImageView.setPivotX(0f);
//        expandedImageView.setPivotY(0f);
//
//        // Construct and run the parallel animation of the four translation and
//        // scale properties (X, Y, SCALE_X, and SCALE_Y).
//        AnimatorSet set = new AnimatorSet();
//        set
//                .play(ObjectAnimator.ofFloat(expandedImageView, View.X,
//                        startBounds.left, finalBounds.left))
//                .with(ObjectAnimator.ofFloat(expandedImageView, View.Y,
//                        startBounds.top, finalBounds.top))
//                .with(ObjectAnimator.ofFloat(expandedImageView, View.SCALE_X,
//                        startScale, 1f))
//                .with(ObjectAnimator.ofFloat(expandedImageView,
//                        View.SCALE_Y, startScale, 1f));
//        set.setDuration(mShortAnimationDuration);
//        set.setInterpolator(new DecelerateInterpolator());
//        set.addListener(new AnimatorListenerAdapter() {
//            @Override
//            public void onAnimationEnd(Animator animation) {
//                mCurrentAnimator = null;
//            }
//
//            @Override
//            public void onAnimationCancel(Animator animation) {
//                mCurrentAnimator = null;
//            }
//        });
//        set.start();
//        mCurrentAnimator = set;
//
//        // Upon clicking the zoomed-in image, it should zoom back down
//        // to the original bounds and show the thumbnail instead of
//        // the expanded image.
//        final float startScaleFinal = startScale;
//        expandedImageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (mCurrentAnimator != null) {
//                    mCurrentAnimator.cancel();
//                }
//
//                // Animate the four positioning/sizing properties in parallel,
//                // back to their original values.
//                AnimatorSet set = new AnimatorSet();
//                set.play(ObjectAnimator
//                        .ofFloat(expandedImageView, View.X, startBounds.left))
//                        .with(ObjectAnimator
//                                .ofFloat(expandedImageView,
//                                        View.Y,startBounds.top))
//                        .with(ObjectAnimator
//                                .ofFloat(expandedImageView,
//                                        View.SCALE_X, startScaleFinal))
//                        .with(ObjectAnimator
//                                .ofFloat(expandedImageView,
//                                        View.SCALE_Y, startScaleFinal));
//                set.setDuration(mShortAnimationDuration);
//                set.setInterpolator(new DecelerateInterpolator());
//                set.addListener(new AnimatorListenerAdapter() {
//                    @Override
//                    public void onAnimationEnd(Animator animation) {
//                        thumbView.setAlpha(1f);
//                        expandedImageView.setVisibility(View.GONE);
//                        mCurrentAnimator = null;
//                    }
//
//                    @Override
//                    public void onAnimationCancel(Animator animation) {
//                        thumbView.setAlpha(1f);
//                        expandedImageView.setVisibility(View.GONE);
//                        mCurrentAnimator = null;
//                    }
//                });
//                set.start();
//                mCurrentAnimator = set;
//            }
//        });
//    }


//}