package com.github.nisrulz.sensey;

import com.github.nisrulz.sensey.FlipDetector.FlipListener;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static com.github.nisrulz.sensey.SensorUtils.testAccelerometerEvent;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@RunWith(MockitoJUnitRunner.class)
public class FlipDetectorTest {

  @Mock private FlipListener mockListener;
  private FlipDetector testFlipDetector;

  @Before
  public void setUp() {
    testFlipDetector = new FlipDetector(mockListener);
  }

  @Test
  public void detectFlipWithMiddleFaceUpValue() {
    testFlipDetector.onSensorChanged(testAccelerometerEvent(new float[] { 0, 0, 9.5f }));
    verify(mockListener, only()).onFaceUp();
  }

  @Test
  public void notDetectFlipWithMinFaceUpValue() {
    testFlipDetector.onSensorChanged(testAccelerometerEvent(new float[] { 0, 0, 9 }));
    verifyNoMoreInteractions(mockListener);
  }

  @Test
  public void notDetectFlipWithMaxFaceUpValue() {
    testFlipDetector.onSensorChanged(testAccelerometerEvent(new float[] { 0, 0, 10 }));
    verifyNoMoreInteractions(mockListener);
  }

  @Test
  public void detectFlipWithMiddleFaceDownValue() {
    testFlipDetector.onSensorChanged(testAccelerometerEvent(new float[] { 0, 0, -9.5f }));
    verify(mockListener, only()).onFaceDown();
  }

  @Test
  public void notDetectFlipWithMinFaceDownValue() {
    testFlipDetector.onSensorChanged(testAccelerometerEvent(new float[] { 0, 0, -10 }));
    verifyNoMoreInteractions(mockListener);
  }

  @Test
  public void notDetectFlipWithMaxFaceDownValue() {
    testFlipDetector.onSensorChanged(testAccelerometerEvent(new float[] { 0, 0, -9 }));
    verifyNoMoreInteractions(mockListener);
  }

  @Test
  public void notDetectFlipWithOtherValue() {
    testFlipDetector.onSensorChanged(testAccelerometerEvent(new float[] { 0, 0, 0 }));
    verifyNoMoreInteractions(mockListener);
  }

  @Test(expected = ArrayIndexOutOfBoundsException.class)
  public void exceptionWithArrayLessThenThreeElements() {
    testFlipDetector.onSensorChanged(testAccelerometerEvent(new float[] { 0, 0 }));
    verifyNoMoreInteractions(mockListener);
  }
}
