# Computer Programming 2 - MotorPH Development Checklist

## ✅ Before Starting Development

### **Environment Check**

- [ ] Java 24 installed and in PATH
- [ ] Maven installed and working
- [ ] VS Code with Java extensions
- [ ] Project opens without errors

### **Quick Test**

```cmd
cd motorph_payroll_system
java -version          # Should show Java 24
mvn -version          # Should show Maven version
mvn clean compile    # Should compile without errors
```

## 🔧 **Daily Workflow**

### **Starting Work Session**

1. Open VS Code in project folder
2. Check if there are any compilation errors in Problems panel
3. Run: `mvn clean compile` to verify everything builds
4. Test run: `mvn exec:java` to ensure app starts

### **Before Making Changes**

1. Make sure current version works: `mvn exec:java`
2. Commit current state: `git add . && git commit -m "Working state"`
3. Make your changes
4. Test immediately: `mvn clean compile exec:java`

### **Common Issues & Solutions**

#### **ClassNotFoundException**

- **Cause**: Dependencies not in classpath
- **Solution**: Use `mvn exec:java` instead of direct `java` command

#### **Compilation Errors**

- **Cause**: Java version mismatch or syntax errors
- **Solution**: Check `pom.xml` has correct Java version (24)

#### **CSV File Not Found**

- **Cause**: Wrong working directory
- **Solution**: Ensure CSV files are in project root, run from correct directory

#### **GUI Doesn't Appear**

- **Cause**: View classes not compiled or wrong main class
- **Solution**: Verify MainFrame.class exists in target/classes

## **Emergency Recovery**

If project breaks completely:

1. `git status` - check what changed
2. `git restore .` - undo all changes to last commit
3. `mvn clean` - remove all compiled files
4. `mvn compile` - rebuild everything
5. `mvn exec:java` - test if working again

## **Key Files to Never Delete**

- `pom.xml` - Maven configuration
- `src/main/java/com/motorph/Main.java` - Entry point
- `employeeDetails.csv` - Employee data
- `attendanceRecord.csv` - Attendance data

## **Best Practices for CP2**

- Always use Maven commands instead of direct javac
- Keep CSV files in project root directory
- Test after every major change
- Use descriptive commit messages
- Don't modify pom.xml unless necessary
