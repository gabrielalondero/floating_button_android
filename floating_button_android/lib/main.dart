import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});


  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Demo',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      home: Home(),
    );
  }
}

class Home extends StatefulWidget {
  const Home({super.key});

  @override
  State<Home> createState() => _HomeState();
}

class _HomeState extends State<Home> {
  static const platform = MethodChannel('floating_button');
  int count = 0;


  @override
  void initState() {
    super.initState();
    //quando manda uma mensagem do android nativo para o flutter
    //entra na função e verifica se o comando foi o 'touch'
    //e então incrementa o contador
    platform.setMethodCallHandler((methodCall) async {
      if(methodCall.method == "touch"){
        setState(() {
          count++;
        });
      }
    });

  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Floating Button Demo'),
        centerTitle: true,
      ),
      body: Padding(
        padding: const EdgeInsets.all(8),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.stretch,
          children: [
            Text(
              '$count',
              textAlign: TextAlign.center,
              style: const TextStyle(
                fontSize: 50,
              ),
            ),
            ElevatedButton(
              onPressed: () {
                //chama o método do android nativo
                platform.invokeMethod('create');
              },
              child: const Text('Create'),
            ),
            ElevatedButton(
              onPressed: () {
                //chama o método do android nativo
                platform.invokeMethod('show');
              },
              child: const Text('Show'),
            ),
            ElevatedButton(
              onPressed: () {
                //chama o método do android nativo
                platform.invokeMethod('hide');
              },
              child: const Text('Hide'),
            ),
            ElevatedButton(
              onPressed: () {
                //chama o método de forma assíncrona e ele retorna se o botão está aparecendo
                platform.invokeMethod('isShowing').then((isShowing) {
                  print(isShowing);
                });
              },
              child: const Text('Verify'),
            ),
          ],
        ),
      ),
    );
  }
}


